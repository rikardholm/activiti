package rikardholm.transfer.workflow.junit;

import com.google.common.base.Optional;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.Job;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import rikardholm.insurance.application.messaging.OutboxRepository;
import rikardholm.insurance.application.messaging.message.InsuranceCreatedResponse;
import rikardholm.insurance.application.messaging.message.PersonDoesNotExistResponse;
import rikardholm.insurance.application.spar.SparResult;
import rikardholm.insurance.common.test.database.InMemoryDatabase;
import rikardholm.insurance.common.test.database.InMemoryDatabaseTestExecutionListener;
import rikardholm.insurance.domain.customer.Address;
import rikardholm.insurance.domain.customer.Customer;
import rikardholm.insurance.domain.customer.CustomerRepository;
import rikardholm.insurance.domain.customer.PersonalIdentifier;
import rikardholm.insurance.domain.insurance.Insurance;
import rikardholm.insurance.domain.insurance.InsuranceNumber;
import rikardholm.insurance.domain.insurance.InsuranceRepository;
import rikardholm.insurance.infrastructure.fake.FakeSparService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static rikardholm.insurance.common.test.hamcrest.OptionalMatchers.hasValue;
import static rikardholm.insurance.common.test.hamcrest.OptionalMatchers.isAbsent;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:META-INF/insurance/spring/domain-context.xml",
        "classpath:META-INF/insurance/spring/messaging-context.xml",
        "classpath:META-INF/insurance/spring/insurance-transfer-context.xml",
        "classpath:/META-INF/insurance/spring/activiti.spring.cfg.xml",
        "classpath:/META-INF/insurance/spring/in-memory-application-context.xml",
        "classpath:/test/spring/activiti-datasource-inmemory.cfg.xml",
        InMemoryDatabaseTestExecutionListener.IN_MEMORY_DATASOURCE})
@InMemoryDatabase
public class InsuranceTransferTest {
    public static final PersonalIdentifier MISSING_PERSONAL_IDENTIFIER = PersonalIdentifier.of("450918-5968");
    public static final String OCR = "234547576767894567";
    public static final Address ADDRESS = Address.of("Spartacusvägen 8");
    //Activiti services
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private ManagementService managementService;

    //Domain services
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private InsuranceRepository insuranceRepository;
    @Autowired
    private FakeSparService fakeSparService;
    @Autowired
    private OutboxRepository outboxRepository;
    public static final PersonalIdentifier EXISTING_PERSONAL_IDENTIFIER = PersonalIdentifier.of("670914-5687");
public static final PersonalIdentifier OTHER_PERSONAL_IDENTIFIER = PersonalIdentifier.of("940525-3142");
    @Test
    public void person_missing_in_SPAR() throws Exception {
        Optional<SparResult> existing = fakeSparService.findBy(MISSING_PERSONAL_IDENTIFIER);
        assertThat(existing, isAbsent());

        Map<String, Object> properties = new HashMap<>();
        properties.put("personalIdentifier", MISSING_PERSONAL_IDENTIFIER);
        properties.put("ocr", OCR);

        runtimeService.startProcessInstanceByMessage("create-insurance", OCR, properties);

        when_all_jobs_within_X_days_are_executed(10);

        List<PersonDoesNotExistResponse> responses = outboxRepository.find(PersonDoesNotExistResponse.class);

        assertThat(responses, hasSize(1));
        assertThat(responses, hasItem(allOf(personalIdentifier(MISSING_PERSONAL_IDENTIFIER), ocr(OCR))));
    }

    @Test
    public void person_who_exists_in_SPAR() throws Exception {
        fakeSparService.add(EXISTING_PERSONAL_IDENTIFIER, ADDRESS);

        Map<String, Object> properties = new HashMap<>();
        properties.put("personalIdentifier", EXISTING_PERSONAL_IDENTIFIER);
        properties.put("ocr", OCR);

        runtimeService.startProcessInstanceByMessage("create-insurance", OCR, properties);

        when_all_jobs_within_X_days_are_executed(10);

        Optional<? extends Customer> customer = customerRepository.findBy(EXISTING_PERSONAL_IDENTIFIER);

        assertThat(customer, hasValue(hasAddress(ADDRESS)));

        List<? extends Insurance> insurances = insuranceRepository.findBy(customer.get());

        assertThat(insurances, hasSize(1));

        InsuranceNumber insuranceNumber = insurances.get(0).getInsuranceNumber();

        List<InsuranceCreatedResponse> insuranceCreatedResponses = outboxRepository.find(InsuranceCreatedResponse.class);
        assertThat(insuranceCreatedResponses, hasSize(1));
        InsuranceCreatedResponse response = insuranceCreatedResponses.get(0);
        assertThat(response.ocr, equalTo(OCR));
        assertThat(response.insuranceNumber, equalTo(insuranceNumber));
        assertThat(response.personalIdentifier, equalTo(EXISTING_PERSONAL_IDENTIFIER));
    }

    @Test
    public void BGC_transfer() throws Exception {
        fakeSparService.add(OTHER_PERSONAL_IDENTIFIER, ADDRESS);

        Map<String, Object> properties = new HashMap<>();
        properties.put("personalIdentifier", OTHER_PERSONAL_IDENTIFIER);
        String ocr = "5450897";
        properties.put("ocr", ocr);

        runtimeService.startProcessInstanceByMessage("create-insurance", ocr, properties);

        when_all_jobs_within_X_days_are_executed(10);

        Execution execution = runtimeService.createExecutionQuery()
                .messageEventSubscriptionName("bgc")
                .processVariableValueEquals("ocr", ocr)
                .singleResult();

        assertThat(execution, notNullValue());

        Map<String, Object> eventProperties = new HashMap<String, Object>();
        properties.put("amount", 56000);
        runtimeService.messageEventReceived("bgc", execution.getId(), eventProperties);
    }

    private void when_all_jobs_within_X_days_are_executed(int days) {
        Date date = DateTime.now().plus(Days.days(days)).toDate();
        Job job = managementService.createJobQuery()
                .duedateLowerThan(date)
                .singleResult();

        assertThat(job, notNullValue());
        managementService.executeJob(job.getId());
    }


    private Matcher<? super PersonDoesNotExistResponse> ocr(final String flyttId) {
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

    private Matcher<Customer> hasAddress(final Address address) {
        return new TypeSafeMatcher<Customer>() {
            @Override
            protected boolean matchesSafely(Customer item) {
                return item.getAddress().equals(address);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(" has address ").appendValue(address);
            }
        };
    }
}
