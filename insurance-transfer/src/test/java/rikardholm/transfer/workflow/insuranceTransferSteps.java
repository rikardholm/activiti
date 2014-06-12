package rikardholm.transfer.workflow;

import com.google.common.base.Optional;
import cucumber.api.PendingException;
import cucumber.api.java.sv.Givet;
import cucumber.api.java.sv.När;
import cucumber.api.java.sv.Och;
import cucumber.api.java.sv.Så;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.Execution;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import rikardholm.insurance.application.messaging.OutboxRepository;
import rikardholm.insurance.application.messaging.message.InsuranceCreatedResponse;
import rikardholm.insurance.application.messaging.message.PersonDoesNotExistResponse;
import rikardholm.insurance.application.spar.SparResult;
import rikardholm.insurance.application.spar.SparService;
import rikardholm.insurance.common.test.OptionalMatchers;
import rikardholm.insurance.domain.customer.Address;
import rikardholm.insurance.domain.customer.Customer;
import rikardholm.insurance.domain.customer.CustomerRepository;
import rikardholm.insurance.domain.customer.PersonalIdentifier;
import rikardholm.insurance.domain.insurance.Insurance;
import rikardholm.insurance.domain.insurance.InsuranceNumber;
import rikardholm.insurance.domain.insurance.InsuranceRepository;
import rikardholm.insurance.infrastructure.fake.FakeSparService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static rikardholm.insurance.common.test.OptionalMatchers.isAbsent;
import static rikardholm.insurance.common.test.OptionalMatchers.isPresent;

@Component
@ContextConfiguration("classpath*:test/cucumber.xml")
public class InsuranceTransferSteps {
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private InsuranceRepository insuranceRepository;
    @Autowired
    private FakeSparService fakeSparService;
    @Autowired
    private OutboxRepository outboxRepository;


    @När("^vi får ett meddelande med personnummer (\\d{6}-\\d{4})$")
    public void vi_får_ett_meddelande_med_personnummer_(String personnummer) throws Throwable {
        PersonalIdentifier personalIdentifier = PersonalIdentifier.of(personnummer);
        Address address = Address.of("Skogsvägen 8");
        fakeSparService.add(personalIdentifier, address);

        Map<String, Object> properties = new HashMap<>();
        properties.put("personalIdentifier", personalIdentifier);
        properties.put("ocr", "123456789");
        runtimeService.startProcessInstanceByKey("insurance-transfer", personalIdentifier.toString(), properties);
    }

    @Givet("^att personnummer (\\d{6}-\\d{4}) inte finns i SPAR$")
    public void att_personnummer_inte_finns_i_SPAR(String personnummer) throws Throwable {
        Optional<SparResult> existing = fakeSparService.findBy(PersonalIdentifier.of(personnummer));
        assertThat(existing, isAbsent());
    }


    @När("^vi får ett meddelande med personnummer (\\d{6}-\\d{4}) och flyttId (\\d+)$")
    public void vi_får_ett_meddelande_med_personnummer_och_flyttId(String personnummer, String flyttId) throws Throwable {
        Map<String, Object> properties = new HashMap<>();
        properties.put("personalIdentifier", PersonalIdentifier.of(personnummer));
        properties.put("ocr", flyttId);

        runtimeService.startProcessInstanceByMessage("create-insurance",flyttId,properties);
    }

    @Så("^skickar vi ett 6b meddelande för personnummer (\\d{6}-\\d{4}) och flyttId (\\d+)$")
    public void skickar_vi_ett_6b_meddelande_för_personnummer_och_flyttId(String personnummer, String flyttId) throws Throwable {
        PersonalIdentifier personalIdentifier = PersonalIdentifier.of(personnummer);

        List<PersonDoesNotExistResponse> responses = outboxRepository.find(PersonDoesNotExistResponse.class);

        assertThat(responses, hasSize(1));
        assertThat(responses, hasItem(allOf(personalIdentifier(personalIdentifier), flyttId(flyttId))));
    }

    @Och("^det skickas ett 8z meddelande med flyttId (\\d+), personnummer (\\d{6}-\\d{4}) och det nya försäkringsnummret$")
    public void det_skickas_ett_z_meddelande_med_flyttId_personnummer_och_rätt_försäkringsnummer(String flyttId, String personnummer) throws Throwable {
        PersonalIdentifier personalIdentifier = PersonalIdentifier.of(personnummer);
        Optional<? extends Customer> customer = customerRepository.findBy(personalIdentifier);
        assertThat(customer, isPresent());
        List<? extends Insurance> insurances = insuranceRepository.findBy(customer.get());
        assertThat(insurances, hasSize(1));
        InsuranceNumber insuranceNumber = insurances.get(0).getInsuranceNumber();

        List<InsuranceCreatedResponse> insuranceCreatedResponses = outboxRepository.find(InsuranceCreatedResponse.class);
        assertThat(insuranceCreatedResponses, hasSize(1));
        InsuranceCreatedResponse response = insuranceCreatedResponses.get(0);
        assertThat(response.ocr, equalTo(flyttId));
        assertThat(response.insuranceNumber, equalTo(insuranceNumber));
        assertThat(response.personalIdentifier, equalTo(personalIdentifier));
    }

    @När("^vi får ett meddelande från bankgirocentralen med ocr (\\d+) och (\\d+)kr$")
    public void vi_får_ett_meddelande_från_bankgirocentralen_med_ocr(String ocr, Integer amount) throws Throwable {
        Execution execution = runtimeService.createExecutionQuery()
                .messageEventSubscriptionName("bgc")
                .processVariableValueEquals("ocr", ocr)
                .singleResult();

        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("amount",amount);
        runtimeService.messageEventReceived("bgc", execution.getId(), properties);
    }

    private Matcher<? super PersonDoesNotExistResponse> flyttId(final String flyttId) {
        return new TypeSafeMatcher<PersonDoesNotExistResponse>() {
            @Override
            protected boolean matchesSafely(PersonDoesNotExistResponse item) {
                return flyttId.equals(item.ocr);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("FlyttId=").appendValue(flyttId);
            }
        };
    }

    private Matcher<? super PersonDoesNotExistResponse> personalIdentifier(final PersonalIdentifier personalIdentifier) {
        return new TypeSafeMatcher<PersonDoesNotExistResponse>() {

            @Override
            protected boolean matchesSafely(PersonDoesNotExistResponse item) {
                return personalIdentifier.equals(item.personalIdentifier);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("PersonalIdentifier=").appendValue(personalIdentifier);
            }
        };
    }
}
