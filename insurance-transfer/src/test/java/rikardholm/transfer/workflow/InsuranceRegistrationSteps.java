package rikardholm.transfer.workflow;

import com.google.common.base.Optional;
import cucumber.api.java.sv.*;
import org.activiti.engine.FormService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import rikardholm.insurance.domain.customer.*;
import rikardholm.insurance.domain.insurance.Insurance;
import rikardholm.insurance.domain.insurance.InsuranceRepository;
import rikardholm.insurance.infrastructure.fake.FakeSparService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static rikardholm.insurance.common.test.hamcrest.OptionalMatchers.hasValue;
import static rikardholm.insurance.common.test.hamcrest.OptionalMatchers.*;

@Component
@ContextConfiguration("classpath*:test/cucumber.xml")
public class InsuranceRegistrationSteps {
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private FormService formService;
    @Autowired
    private TaskService taskService;

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private FakeSparService fakeSparService;
    @Autowired
    private InsuranceRepository insuranceRepository;

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
        Optional<? extends Customer> customer = customerRepository.findBy(PersonalIdentifier.of(personnummer));

        assertThat(customer, hasValue(hasAddress(Address.of(address))));
    }

    @Och("^(?:att )?personnummer (\\d{6}-\\d{4}) finns i SPAR med address \"([^\"]*)\"$")
    public void personnummer_finns_i_SPAR_med_address(String personnummer, String address) throws Throwable {
        fakeSparService.add(PersonalIdentifier.of(personnummer), Address.of(address));
    }

    @Och("^(?:att )?personnummer (\\d{6}-\\d{4}) har skyddad identitet i SPAR$")
    public void personnummer_har_skyddad_identitet_i_SPAR(String personnummer) throws Throwable {
        fakeSparService.addSecret(PersonalIdentifier.of(personnummer));
    }

    @Men("^om SPAR är nere$")
    public void om_SPAR_är_nere() throws Throwable {
        fakeSparService.makeUnavailable();
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

        formService.submitStartFormData(processDefinition.getId(), properties);
    }

    @Givet("^ett personnummer (\\d{6}-\\d{4}) som inte existerar i SPAR$")
    public void ett_personnummer_som_inte_existerar_i_SPAR(String personnummer) throws Throwable {
           assertThat(fakeSparService.findBy(PersonalIdentifier.of(personnummer)), isAbsent());
    }

    @Och("^MO utreder i ett ärende att personen har adress \"([^\"]*)\"$")
    public void MO_utreder_i_ett_ärende_att_personen_har_adress(String address) throws Throwable {
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("register-insurance")
                .taskCandidateGroup("Middle Office")
                .singleResult();

        assertThat(task, is(notNullValue()));

        taskService.claim(task.getId(),"Rikard");

        Map<String, String> formValues = new HashMap<>();
        formValues.put("address", address);
        formService.submitTaskFormData(task.getId(), formValues);
    }

    @Givet("^att SPAR är uppe$")
    public void att_SPAR_är_uppe() throws Throwable {
        fakeSparService.makeAvailable();
    }
}
