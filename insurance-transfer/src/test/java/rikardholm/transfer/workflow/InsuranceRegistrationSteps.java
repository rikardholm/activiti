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
import rikardholm.insurance.common.test.OptionalMatchers;
import rikardholm.insurance.domain.customer.*;
import rikardholm.insurance.domain.insurance.Insurance;
import rikardholm.insurance.domain.insurance.InsuranceRepository;
import rikardholm.insurance.infrastructure.fake.FakeSparService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static rikardholm.insurance.common.test.OptionalMatchers.hasValue;
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

    @Givet("^en person med personnummer (\\d{6}-\\d{4}) som inte är kund hos företaget$")
    public void en_blivande_kund_med_personnummer(String personalIdentifier) {
        Optional<? extends Customer> customer = customerRepository.findBy(PersonalIdentifier.of(personalIdentifier));

        if (customer.isPresent()) {
            for (Insurance insurance : insuranceRepository.findBy(customer.get())) {
                insuranceRepository.delete(insurance);
            }

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
        Map<String, String> properties = new HashMap<>();
        properties.put("personalIdentifier", personalIdentifier);

        submitForm(properties);
    }

    @Så("^(?:det )?skapas en försäkring kopplad till kundkonto (\\d{6}-\\d{4})$")
    public void skapas_en_försäkring_kopplad_till_kundens_konto(String personalIdentifier) {
        assertThat(processInstance.isEnded(), is(true));

        Optional<? extends Customer> customer = customerRepository.findBy(PersonalIdentifier.of(personalIdentifier));

        assertThat(customer, isPresent());

        List<? extends Insurance> insurances = insuranceRepository.findBy(customer.get());

        assertThat(insurances, hasSize(1));
    }

    @När("^vi tar emot en anmälan för personnummer (\\d{6}-\\d{4}) med address \"([^\"]*)\"$")
    public void vi_tar_emot_en_anmälan_för_personnummer_med_address(String personnummer, String address) throws Throwable {
        Map<String, String> properties = new HashMap<>();
        properties.put("personalIdentifier", personnummer);
        properties.put("address", address);

        submitForm(properties);
    }

    @Så("^skapas ett kundkonto för personnummer (\\d{6}-\\d{4}) med address \"([^\"]*)\"$")
    public void skapas_ett_kundkonto_för_personnummer_med_address(String personnummer, String address) throws Throwable {
        assertThat(processInstance.isEnded(), is(true));

        Optional<? extends Customer> customer = customerRepository.findBy(PersonalIdentifier.of(personnummer));

        assertThat(customer, hasValue(hasAddress(Address.of(address))));
    }

    @Givet("^att adressen för (\\d{6}-\\d{4}) i SPAR är \"([^\"]*)\"$")
    public void att_adressen_för_i_SPAR_är(String personnummer, String address) throws Throwable {
        fakeSparService.add(PersonalIdentifier.of(personnummer), Address.of(address));
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

    private void submitForm(Map<String, String> properties) {
        ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionKey("register-insurance")
                .singleResult();

        processInstance = formService.submitStartFormData(processDefinition.getId(), properties);
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
