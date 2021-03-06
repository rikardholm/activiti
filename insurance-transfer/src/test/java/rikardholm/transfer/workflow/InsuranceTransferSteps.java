package rikardholm.transfer.workflow;

import com.google.common.base.Optional;
import cucumber.api.java.sv.Givet;
import cucumber.api.java.sv.När;
import cucumber.api.java.sv.Och;
import cucumber.api.java.sv.Så;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.Job;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import rikardholm.insurance.application.messaging.Message;
import rikardholm.insurance.application.messaging.MessageRepository;
import rikardholm.insurance.application.spar.SparResult;
import rikardholm.insurance.domain.customer.Customer;
import rikardholm.insurance.domain.customer.CustomerRepository;
import rikardholm.insurance.domain.customer.PersonalIdentifier;
import rikardholm.insurance.domain.insurance.Insurance;
import rikardholm.insurance.domain.insurance.InsuranceNumber;
import rikardholm.insurance.domain.insurance.InsuranceRepository;
import rikardholm.insurance.infrastructure.fake.FakeSparService;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static rikardholm.insurance.common.test.hamcrest.JsonMatcher.*;
import static rikardholm.insurance.common.test.hamcrest.OptionalMatchers.isAbsent;
import static rikardholm.insurance.common.test.hamcrest.OptionalMatchers.isPresent;

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
    private MessageRepository outbox;
    @Autowired
    private ManagementService managementService;

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

        runtimeService.startProcessInstanceByMessage("create-insurance", flyttId, properties);
    }

    @Så("^skickar vi ett 6b meddelande för personnummer (\\d{6}-\\d{4}) och flyttId (\\d+)$")
    public void skickar_vi_ett_6b_meddelande_för_personnummer_och_flyttId(String personnummer, String flyttId) throws Throwable {
        PersonalIdentifier personalIdentifier = PersonalIdentifier.of(personnummer);


        List<Message> messages = outbox.receivedAfter(Instant.now().minus(Duration.ofHours(1)));

        assertThat(messages, hasSize(1));
        String payload = messages.get(0).getPayload();
        assertThat(payload, isJson(withProperty("messageType", equalTo("\"person-does-not-exist\""))));
        assertThat(payload, isJson(withProperty("personalIdentifier", equalTo("\"" + personnummer + "\""))));
        assertThat(payload, isJson(withProperty("ocr", equalTo("\"" + flyttId + "\""))));
    }

    @Och("^det skickas ett 8z meddelande med flyttId (\\d+), personnummer (\\d{6}-\\d{4}) och det nya försäkringsnummret$")
    public void det_skickas_ett_z_meddelande_med_flyttId_personnummer_och_rätt_försäkringsnummer(String flyttId, String personnummer) throws Throwable {
        PersonalIdentifier personalIdentifier = PersonalIdentifier.of(personnummer);
        Optional<? extends Customer> customer = customerRepository.findBy(personalIdentifier);
        assertThat(customer, isPresent());
        List<? extends Insurance> insurances = insuranceRepository.findBy(customer.get());
        assertThat(insurances, hasSize(1));
        InsuranceNumber insuranceNumber = insurances.get(0).getInsuranceNumber();

        List<Message> messages = outbox.receivedAfter(Instant.now().minus(Duration.ofHours(1)));

        assertThat(messages, hasSize(1));
        String payload = messages.get(0).getPayload();
        assertThat(payload, isJson(withProperty("messageType", equalTo("\"insurance-created\""))));
        assertThat(payload, isJson(withProperty("personalIdentifier", equalTo("\"" + personnummer + "\""))));
        assertThat(payload, isJson(withProperty("ocr", equalTo("\"" + flyttId + "\""))));
        assertThat(payload, isJson(withProperty("insuranceNumber", equalTo(insuranceNumber.getValue().toString()))));
    }

    @När("^vi får ett meddelande från bankgirocentralen med ocr (\\d+) och (\\d+)kr$")
    public void vi_får_ett_meddelande_från_bankgirocentralen_med_ocr(String ocr, Integer amount) throws Throwable {
        Execution execution = runtimeService.createExecutionQuery()
                .messageEventSubscriptionName("bgc")
                .processVariableValueEquals("ocr", ocr)
                .singleResult();

        assertThat(execution, notNullValue());

        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("amount",amount);
        runtimeService.messageEventReceived("bgc", execution.getId(), properties);
    }

    @Och("^väntar (\\d+) dagar$")
    public void väntar_dagar(int arg1) throws Throwable {
        Date date = DateTime.now().plus(Days.days(arg1)).toDate();
        Job job = managementService.createJobQuery()
                .duedateLowerThan(date)
                .singleResult();

        assertThat(job, notNullValue());
        managementService.executeJob(job.getId());
    }
}
