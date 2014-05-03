package rikardholm.transfer.workflow;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java.sv.Givet;
import cucumber.api.java.sv.När;
import cucumber.api.java.sv.Så;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import rikardholm.insurance.service.PersonalIdentifier;
import rikardholm.insurance.service.insurance.*;
import rikardholm.insurance.service.spar.internal.FakeSparService;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static rikardholm.insurance.service.insurance.Builders.aCustomer;

@Component
@ContextConfiguration("classpath*:cucumber.xml")
@DirtiesContext
public class Steps {
    private static Logger log = LoggerFactory.getLogger(Steps.class);
    @Autowired
    private InsuranceRepository insuranceRepository;

    public static final PersonalIdentifier PERSONAL_IDENTIFIER = PersonalIdentifier.of("670913-4506");
    public static final Customer CUSTOMER = aCustomer().withPersonalIdentifier(PERSONAL_IDENTIFIER).build();
    public static final InsuranceNumber INSURANCE_NUMBER = InsuranceNumber.of(35968L);


    @Givet("^en försäkring som tillhör en person$")
    public void en_försäkring_som_tillhör_en_person() throws Throwable {
        Insurance insurance = Builders.anInsurance()
                .withInsuranceNumber(INSURANCE_NUMBER)
                .belongsTo(CUSTOMER)
                .build();

        insuranceRepository.create(insurance);
    }

    @Givet("^att det inte finns någon försäkring som tillhör en person")
    public void att_det_inte_finns_någon_försäkring_som_tillhör_en_person() {
        List<Insurance> insurances = insuranceRepository.findBy(CUSTOMER);
        assertThat(insurances, is(empty()));
    }

    @När("^vi tar emot en förfrågan om personen$")
    public void vi_tar_emot_en_förfrågan_om_personen() throws Throwable {
        throw new PendingException();
    }

    @Så("^svarar vi med information om försäkringen$")
    public void svarar_vi_med_information_om_försäkringen() throws Throwable {
        throw new PendingException();
    }

    @Så("^svarar vi att personen inte har några försäkringar hos oss$")
    public void svarar_vi_att_personen_inte_har_några_försäkringar_hos_oss() {

    }

    /*@Given("^no insurance belongs to customer (\\d+)-(\\d+)$")
    public void no_insurance_belongs_to_customer(int arg1, int arg2) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    } */
        /*
    @Then("^the outgoing message should be no-information$")
    public void the_outgoing_message_should_be_no_information() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }     */
}
