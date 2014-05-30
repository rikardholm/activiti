package rikardholm.transfer.workflow;

import com.google.common.base.Optional;
import cucumber.api.PendingException;
import cucumber.api.java.sv.*;
import org.activiti.engine.FormService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import rikardholm.insurance.domain.customer.CustomerBuilder;
import rikardholm.insurance.domain.customer.Address;
import rikardholm.insurance.domain.customer.Customer;
import rikardholm.insurance.domain.customer.CustomerRepository;
import rikardholm.insurance.domain.customer.PersonalIdentifier;
import rikardholm.insurance.domain.insurance.Insurance;
import rikardholm.insurance.domain.insurance.InsuranceRepository;
import rikardholm.insurance.infrastructure.fake.FakeSparService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static rikardholm.insurance.common.test.OptionalMatchers.isPresent;

@Component
@ContextConfiguration("classpath*:test/cucumber.xml")
public class InsuranceRegistrationSteps {

    public static final PersonalIdentifier PERSONAL_IDENTIFIER = PersonalIdentifier.of("561102-3048");
    public static final Customer CUSTOMER = CustomerBuilder.aCustomer()
            .withPersonalIdentifier(PERSONAL_IDENTIFIER)
            .withAddress(Address.of("Testvägen 80"))
            .build();

    public static final String EXISTERANDE_UPPGIFTER = "Existensplan 8, 42 666 Ingenstans";
    public static final String SPAR_UPPGIFTER = "SPARgatan 511, 120 66 Stockholm";
    public static final String MOS_UPPGIFTER = "MOgränd 234, 117 28 Stockholm";

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private FormService formService;
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private FakeSparService fakeSparService;
    @Autowired
    private InsuranceRepository insuranceRepository;
    private ProcessInstance processInstance;

    @Givet("^en blivande kund med personnummer (\\d{6}-\\d{4}) och adress \"(.*)\"$")
    public void en_blivande_kund_med_personnummer(String personalIdentifier, String adress) {
        Optional<? extends Customer> customer = customerRepository.findBy(PersonalIdentifier.of(personalIdentifier));

        if (customer.isPresent()) {
            customerRepository.delete(customer.get());
        }
    }

    @Och("^en existerande kund med personnummer (\\d{6}-\\d{4}) utan försäkringar$")
    public void en_existerande_kund_med_personnummer(String personalIdentifierString) {
        PersonalIdentifier personalIdentifier = PersonalIdentifier.of(personalIdentifierString);
        if (customerRepository.findBy(personalIdentifier).isPresent()) {
             return;
        }

        Customer customer = CustomerBuilder.aCustomer()
                .withPersonalIdentifier(personalIdentifier)
                .withAddress(Address.of("Verifikationsvägen 45"))
                .build();

        customerRepository.save(customer);
    }

    @När("^vi tar emot en anmälan för personnummer (\\d{6}-\\d{4})$")
    public void vi_tar_emot_en_anmälan_för_personnummer(String personalIdentifier) {
        Map<String, String> properties = new HashMap<String, String>();
        properties.put("personalIdentifier", personalIdentifier);

        ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionKey("register-insurance")
                .singleResult();

        processInstance = formService.submitStartFormData(processDefinition.getId(), properties);
    }

    @Så("^skapas en försäkring kopplad till kundkonto (\\d{6}-\\d{4})$")
    public void skapas_en_försäkring_kopplad_till_kundens_konto(String personalIdentifier) {
        assertThat(processInstance.isEnded(), is(true));

        Optional<? extends Customer> customer = customerRepository.findBy(PersonalIdentifier.of(personalIdentifier));

        assertThat(customer, isPresent());

        List<? extends Insurance> insurances = insuranceRepository.findBy(customer.get());

        assertThat(insurances, hasSize(1));
    }

    @Givet("^en person som är kund hos företaget")
    public void en_person_som_är_kund_hos_företaget() {
        customerRepository.save(CUSTOMER);
    }

    @Givet("^en person som inte är kund hos företaget")
    public void en_person_som_inte_är_kund_hos_företaget() {
        customerRepository.delete(CUSTOMER);
    }

    @Givet("^som finns i SPAR")
    public void som_finns_i_SPAR() {
        fakeSparService.add(PERSONAL_IDENTIFIER, SPAR_UPPGIFTER);
    }

    @Och("^som har skyddad identitet i SPAR")
    public void som_har_skyddad_identitet_i_SPAR() {
        fakeSparService.addSecret(PERSONAL_IDENTIFIER);
    }

    @Och("^att SPAR är nere")
    public void att_SPAR_är_nere() {
        fakeSparService.makeUnavailable();
    }

    @Så("^skapas en försäkring med kundens existerande uppgifter")
    public void skapas_en_försäkring_med_kundens_existerande_uppgifter() {
        List<? extends Insurance> insurances = getInsurances(PERSONAL_IDENTIFIER);

        assertThat(insurances, contains(insuranceWithAddress(EXISTERANDE_UPPGIFTER)));
    }

    @Så("^skapas en försäkring med personens uppgifter i SPAR")
    public void skapas_en_försäkring_med_personens_uppgifter_i_SPAR() {
        List<? extends Insurance> insurances = getInsurances(PERSONAL_IDENTIFIER);

        assertThat(insurances, contains(insuranceWithAddress(SPAR_UPPGIFTER)));
    }

    @Och("^försäkringen skapas med MOs uppgifter")
    public void försäkringen_skapas_med_MOs_uppgifter() {
        List<? extends Insurance> insurances = getInsurances(PERSONAL_IDENTIFIER);

        assertThat(insurances, contains(insuranceWithAddress(MOS_UPPGIFTER)));
    }

    private List<? extends Insurance> getInsurances(PersonalIdentifier personalIdentifier) {
        Optional<? extends Customer> customer = customerRepository.findBy(personalIdentifier);
        assertThat(customer, isPresent());
        return insuranceRepository.findBy(customer.get());
    }

    private Matcher<Insurance> insuranceWithAddress(final String uppgifter) {
        return new TypeSafeMatcher<Insurance>() {
            @Override
            protected boolean matchesSafely(Insurance item) {
                return uppgifter.equals("");
            }

            @Override
            public void describeTo(Description description) {
                 description.appendText("an insurance with address ").appendValue(uppgifter);
            }

            @Override
            protected void describeMismatchSafely(Insurance item, Description mismatchDescription) {
                mismatchDescription.appendText("the address was ").appendValue("");
            }
        };
    }


    @Givet("^att adressen för (\\d+)-(\\d+) i SPAR är \"([^\"]*)\"$")
    public void att_adressen_för_i_SPAR_är(int datum, int crc, String address) throws Throwable {
        throw new PendingException();
    }

    @Så("^skapas en försäkring kopplad till ett kundkonto med personnummer (\\d+)-(\\d+) och adress \"([^\"]*)\"$")
    public void skapas_en_försäkring_kopplad_till_ett_kundkonto_med_personnummer_och_adress(int arg1, int arg2, String arg3) throws Throwable {
        // Express the Regexp above with the code you wish you had
        throw new PendingException();
    }

    @Men("^om (\\d+)-(\\d+) inte finns i SPAR$")
    public void om_inte_finns_i_SPAR(int arg1, int arg2) throws Throwable {
        // Express the Regexp above with the code you wish you had
        throw new PendingException();
    }

    @Så("^utreder MO att personen har adress \"([^\"]*)\"$")
    public void utreder_MO_att_personen_har_adress(String arg1) throws Throwable {
        // Express the Regexp above with the code you wish you had
        throw new PendingException();
    }

    @Men("^om (\\d+)-(\\d+) har skyddad identitet$")
    public void om_har_skyddad_identitet(int arg1, int arg2) throws Throwable {
        // Express the Regexp above with the code you wish you had
        throw new PendingException();
    }

    @Och("^det skapas en försäkring kopplad till ett kundkonto med personnummer (\\d+)-(\\d+) och adress \"([^\"]*)\"$")
    public void det_skapas_en_försäkring_kopplad_till_ett_kundkonto_med_personnummer_och_adress(int arg1, int arg2, String arg3) throws Throwable {
        // Express the Regexp above with the code you wish you had
        throw new PendingException();
    }

    @Men("^om SPAR är nere$")
    public void om_SPAR_är_nere() throws Throwable {
        // Express the Regexp above with the code you wish you had
        throw new PendingException();
    }
}
