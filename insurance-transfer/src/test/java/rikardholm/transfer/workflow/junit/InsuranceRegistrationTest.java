package rikardholm.transfer.workflow.junit;

import com.google.common.base.Optional;
import org.activiti.engine.FormService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import rikardholm.insurance.common.test.database.InMemoryDatabase;
import rikardholm.insurance.common.test.database.InMemoryDatabaseTestExecutionListener;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:META-INF/insurance/spring/domain-context.xml",
        "classpath:META-INF/insurance/spring/insurance-transfer-context.xml",
        "classpath:/META-INF/insurance/spring/activiti.spring.cfg.xml",
        "classpath:/META-INF/insurance/spring/in-memory-application-context.xml",
        InMemoryDatabaseTestExecutionListener.APPLICATION_CONTEXT_PATH,
        "classpath:/test/spring/activiti-datasource-inmemory.cfg.xml"})
@InMemoryDatabase
public class InsuranceRegistrationTest {
    public static final String ADDRESS = "Formulärsvägen 18, 384 90 Sundsvall";
    public static final String PERSONAL_IDENTIFIER = "650416-0646";
    public static final String MO_ADDRESS = "Mosippevägen 138, Göteborg";
    public static final String PROTECTED_PERSONAL_IDENTIFIER = "556644-1234";
    public static final String EXISTING_PERSONAL_IDENTIFIER = "900830-2037";
    //Activiti services
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private FormService formService;
    @Autowired
    private TaskService taskService;

    //Domain services
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private FakeSparService fakeSparService;
    @Autowired
    private InsuranceRepository insuranceRepository;

    @Test
    public void registered_with_address() throws Exception {
        Map<String, String> properties = new HashMap<>();
        properties.put("personalIdentifier", PERSONAL_IDENTIFIER);
        properties.put("address", ADDRESS);

        submitForm(properties);

        Optional<? extends Customer> customer = customerRepository.findBy(PersonalIdentifier.of(PERSONAL_IDENTIFIER));

        assertThat(customer, hasValue(hasAddress(Address.of(ADDRESS))));

        List<? extends Insurance> insurances = insuranceRepository.findBy(customer.get());

        assertThat(insurances, hasSize(1));
    }

    @Test
    public void person_in_SPAR() throws Exception {
        fakeSparService.add(PersonalIdentifier.of(PERSONAL_IDENTIFIER), Address.of(ADDRESS));
        fakeSparService.makeAvailable();

        Map<String, String> properties = new HashMap<>();
        properties.put("personalIdentifier", PERSONAL_IDENTIFIER);

        submitForm(properties);

        Optional<? extends Customer> customer = customerRepository.findBy(PersonalIdentifier.of(PERSONAL_IDENTIFIER));

        assertThat(customer, hasValue(hasAddress(Address.of(ADDRESS))));

        List<? extends Insurance> insurances = insuranceRepository.findBy(customer.get());

        assertThat(insurances, hasSize(1));
    }

    @Test
    public void SPAR_unavailable() throws Exception {
        fakeSparService.makeUnavailable();

        Map<String, String> properties = new HashMap<>();
        properties.put("personalIdentifier", PERSONAL_IDENTIFIER);

        submitForm(properties);

        solve_task_as_MO();

        Optional<? extends Customer> customer = customerRepository.findBy(PersonalIdentifier.of(PERSONAL_IDENTIFIER));

        assertThat(customer, hasValue(hasAddress(Address.of(MO_ADDRESS))));

        List<? extends Insurance> insurances = insuranceRepository.findBy(customer.get());

        assertThat(insurances, hasSize(1));
    }

    @Test
    public void protected_identity() throws Exception {
        fakeSparService.addSecret(PersonalIdentifier.of(PROTECTED_PERSONAL_IDENTIFIER));
        fakeSparService.makeAvailable();

        Map<String, String> properties = new HashMap<>();
        properties.put("personalIdentifier", PROTECTED_PERSONAL_IDENTIFIER);

        submitForm(properties);

        solve_task_as_MO();

        Optional<? extends Customer> customer = customerRepository.findBy(PersonalIdentifier.of(PROTECTED_PERSONAL_IDENTIFIER));

        assertThat(customer, hasValue(hasAddress(Address.of(MO_ADDRESS))));

        List<? extends Insurance> insurances = insuranceRepository.findBy(customer.get());

        assertThat(insurances, hasSize(1));
    }

    @Test
    public void existing_customer() throws Exception {
        PersonalIdentifier personalIdentifier = PersonalIdentifier.of(EXISTING_PERSONAL_IDENTIFIER);

        Customer customer = CustomerBuilder.aCustomer()
                .withPersonalIdentifier(personalIdentifier)
                .withAddress(Address.of("Verifikationsvägen 45"))
                .build();

        customerRepository.save(customer);

        Map<String, String> properties = new HashMap<>();
        properties.put("personalIdentifier", EXISTING_PERSONAL_IDENTIFIER);

        submitForm(properties);

        List<? extends Insurance> insurances = insuranceRepository.findBy(customer);

        assertThat(insurances, hasSize(1));
    }

    private void solve_task_as_MO() {
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("register-insurance")
                .taskCandidateGroup("Middle Office")
                .singleResult();

        assertThat(task, is(notNullValue()));

        taskService.claim(task.getId(), "Rikard");

        Map<String, String> formValues = new HashMap<>();
        formValues.put("address", MO_ADDRESS);
        formService.submitTaskFormData(task.getId(), formValues);
    }

    private void submitForm(Map<String, String> properties) {
        ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionKey("register-insurance")
                .singleResult();

        formService.submitStartFormData(processDefinition.getId(), properties);
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
