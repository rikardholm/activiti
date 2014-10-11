package rikardholm.transfer.workflow;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.sv.Givet;
import cucumber.api.java.sv.När;
import cucumber.api.java.sv.Så;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import rikardholm.insurance.application.messaging.Message;
import rikardholm.insurance.application.messaging.MessageBuilder;
import rikardholm.insurance.application.messaging.MessageRepository;
import rikardholm.insurance.common.test.database.InMemoryDatabaseManager;
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
import static org.hamcrest.Matchers.hasSize;

@Component
@ContextConfiguration("classpath*:test/cucumber.xml")
public class InsuranceInformationSteps {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private InsuranceRepository insuranceRepository;

    @Autowired
    private MessageRepository outbox;

    @Autowired
    private MessageRepository inbox;

    @Autowired
    private ProcessDispatcher processDispatcher;

    private InMemoryDatabaseManager inMemoryDatabaseManager = new InMemoryDatabaseManager();

    public static final PersonalIdentifier PERSONAL_IDENTIFIER = PersonalIdentifier.of("670913-4506");
    public static final Customer CUSTOMER = CustomerBuilder
            .aCustomer()
            .withPersonalIdentifier(PERSONAL_IDENTIFIER)
            .withAddress(Address.of("Testgatan 130, 134 56 Stockholm"))
            .build();
    public static final InsuranceNumber INSURANCE_NUMBER = InsuranceNumber.of(35968L);

    @Before
    public void migrateDatabase() {
        inMemoryDatabaseManager.migrate();
    }

    @After
    public void shutdownDatabase() {
        inMemoryDatabaseManager.shutdownDatabase();
    }


    @Givet("^en person som har en försäkring på företaget$")
    public void en_försäkring_som_tillhör_en_person() throws Throwable {
        Insurance insurance = InsuranceBuilder.anInsurance()
                .withInsuranceNumber(INSURANCE_NUMBER)
                .belongsTo(CUSTOMER)
                .build();

        customerRepository.save(CUSTOMER);
        insuranceRepository.save(insurance);
    }

    @Givet("^en person som saknar försäkringar hos bolaget$")
    public void att_det_inte_finns_någon_försäkring_som_tillhör_en_person() {
        List<? extends Insurance> insurances = insuranceRepository.findBy(CUSTOMER);
        for (Insurance insurance : insurances) {
            insuranceRepository.delete(insurance);
        }

        customerRepository.delete(CUSTOMER);
    }

    @När("^vi tar emot en förfrågan om personen$")
    public void vi_tar_emot_en_förfrågan_om_personen() throws Throwable {
        Message message = MessageBuilder.aMessage()
                .withUUID(UUID.randomUUID())
                .receivedAt(Instant.now())
                .payload("{\"personalIdentifier\":\"" + PERSONAL_IDENTIFIER.getValue() + "\"}")
                .build();

        inbox.append(message);
        processDispatcher.poll();
    }

    @Så("^svarar vi med information om försäkringen$")
    public void svarar_vi_med_information_om_försäkringen() throws Throwable {
        List<Message> insuranceInformationResponses = outbox.receivedAfter(Instant.now().minusSeconds(50));
        assertThat(insuranceInformationResponses, hasSize(1));
        //TODO: Test properly
        //InsuranceInformationResponse insuranceInformationResponse = insuranceInformationResponses.get(0);
        //assertThat(insuranceInformationResponse.personalIdentificationNumber, equalTo(PERSONAL_IDENTIFIER.getValue()));
        //assertThat(insuranceInformationResponse.insuranceNumbers, hasSize(1));
        //assertThat(insuranceInformationResponse.insuranceNumbers.get(0), equalTo(INSURANCE_NUMBER.getValue()));
    }

    @Så("^svarar vi att personen inte har några försäkringar hos oss$")
    public void svarar_vi_att_personen_inte_har_några_försäkringar_hos_oss() {
        List<Message> noInsurancesResponses = outbox.receivedAfter(Instant.now().minusSeconds(50));
        assertThat(noInsurancesResponses, hasSize(1));
        //TODO: Test properly
        //assertThat(noInsurancesResponses.get(0).personalIdentificationNumber, is(equalTo(PERSONAL_IDENTIFIER.getValue())));
    }
}
