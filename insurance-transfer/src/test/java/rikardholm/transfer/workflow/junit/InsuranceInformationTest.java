package rikardholm.transfer.workflow.junit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import rikardholm.insurance.application.messaging.*;
import rikardholm.insurance.application.messaging.message.InsuranceInformationResponse;
import rikardholm.insurance.application.messaging.message.NoInsurancesResponse;
import rikardholm.insurance.common.test.database.InMemoryDatabase;
import rikardholm.insurance.common.test.database.InMemoryDatabaseTestExecutionListener;
import rikardholm.insurance.domain.customer.*;
import rikardholm.insurance.domain.insurance.Insurance;
import rikardholm.insurance.domain.insurance.InsuranceBuilder;
import rikardholm.insurance.domain.insurance.InsuranceNumber;
import rikardholm.insurance.domain.insurance.InsuranceRepository;
import rikardholm.insurance.transfer.ProcessDispatcher;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:META-INF/insurance/spring/insurance-transfer-context.xml",
        "classpath:/META-INF/insurance/spring/activiti.spring.cfg.xml",
        "classpath:/META-INF/insurance/spring/in-memory-application-context.xml",
        "classpath:/META-INF/insurance/spring/domain-context.xml",
        "classpath:/META-INF/insurance/spring/messaging-context.xml",
        "classpath:/test/spring/activiti-datasource-inmemory.cfg.xml",
        InMemoryDatabaseTestExecutionListener.IN_MEMORY_DATASOURCE})
@InMemoryDatabase
public class InsuranceInformationTest {

    public static final PersonalIdentifier PERSONAL_IDENTIFIER = PersonalIdentifier.of("940315-2045");
    public static final Customer CUSTOMER = CustomerBuilder.aCustomer().withPersonalIdentifier(PERSONAL_IDENTIFIER).withAddress(Address.of("333 O'Farrell Street")).build();
    public static final InsuranceNumber INSURANCE_NUMBER = InsuranceNumber.of(234597L);
    public static final PersonalIdentifier NOT_A_CUSTOMER = PersonalIdentifier.of("101010-3344");

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private InsuranceRepository insuranceRepository;

    @Autowired
    private MessageRepository inbox;

    @Autowired
    private MessageRepository outbox;

    @Autowired
    private ProcessDispatcher processDispatcher;

    @Test
    public void should_reply_with_insurance_if_customer_exists() throws Exception {
        Insurance insurance = InsuranceBuilder.anInsurance()
                .withInsuranceNumber(INSURANCE_NUMBER)
                .belongsTo(CUSTOMER)
                .build();

        customerRepository.save(CUSTOMER);
        insuranceRepository.save(insurance);

        when_processing_a_message_for(PERSONAL_IDENTIFIER);

        fail();
        /*
        List<InsuranceInformationResponse> insuranceInformationResponses = outbox.find(InsuranceInformationResponse.class);
        assertThat(insuranceInformationResponses, hasSize(1));
        InsuranceInformationResponse insuranceInformationResponse = insuranceInformationResponses.get(0);
        assertThat(insuranceInformationResponse.personalIdentificationNumber, equalTo(PERSONAL_IDENTIFIER.getValue()));
        assertThat(insuranceInformationResponse.insuranceNumbers, hasSize(1));
        assertThat(insuranceInformationResponse.insuranceNumbers.get(0), equalTo(INSURANCE_NUMBER.getValue()));
        */
    }

    @Test
    public void should_reply_no_insurance_if_customer_has_no_insurance() throws Exception {
        when_processing_a_message_for(NOT_A_CUSTOMER);

        then_we_reply_with_a_message_of_type_and_with_personalidentifier(NoInsurancesResponse.class, NOT_A_CUSTOMER);
    }

    private void when_processing_a_message_for(PersonalIdentifier personalIdentifier) {
        Message message = MessageBuilder.aMessage()
                .withUUID(UUID.randomUUID())
                .receivedAt(Instant.now())
                .payload("{\"personalIdentifier\":\"" + personalIdentifier.getValue() + "\"}")
                .build();

        inbox.append(message);

        processDispatcher.poll();

    }

    private void then_we_reply_with_a_message_of_type_and_with_personalidentifier(Class<NoInsurancesResponse> type, PersonalIdentifier personalIdentifier) {
        fail();
        /*
        List<NoInsurancesResponse> noInsurancesResponses = outboxRepository.find(type);
        assertThat(noInsurancesResponses, hasSize(1));
        assertThat(noInsurancesResponses.get(0).personalIdentificationNumber, is(equalTo(personalIdentifier.getValue())));
        */
    }
}
