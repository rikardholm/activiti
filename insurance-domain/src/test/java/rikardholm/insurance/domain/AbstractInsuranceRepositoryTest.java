package rikardholm.insurance.domain;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExternalResource;
import rikardholm.insurance.common.Optional;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static rikardholm.insurance.common.test.OptionalMatchers.hasValue;
import static rikardholm.insurance.common.test.OptionalMatchers.isAbsent;
import static rikardholm.insurance.domain.Builders.aCustomer;
import static rikardholm.insurance.domain.Builders.anInsurance;

public abstract class AbstractInsuranceRepositoryTest extends AbstractContractTest<InsuranceRepository> {

    private static final InsuranceNumber INSURANCE_NUMBER = InsuranceNumber.of(311768L);

    public static final Customer CUSTOMER = aCustomer()
            .withPersonalIdentifier(PersonalIdentifier.of("840325-9043"))
            .build();

    private static final Insurance INSURANCE = Builders
            .anInsurance()
            .withInsuranceNumber(INSURANCE_NUMBER)
            .belongsTo(CUSTOMER)
            .build();

    @Rule
    public InsuranceRule insuranceRule = new InsuranceRule();

    private InsuranceRepository insuranceRepository;
    private CustomerRepository customerRepository;


    abstract protected CustomerRepository getCustomerRepository();

    @Before
    public void setUp() throws Exception {
        insuranceRepository = getInstance();
        customerRepository = getCustomerRepository();

        insuranceRule.setCustomerRepository(customerRepository);
        insuranceRule.setInsuranceRepository(insuranceRepository);
    }

    @Test
    public void findByInsuranceNumber_should_return_absent_when_Insurance_does_not_exist() {
        Optional<Insurance> result = insuranceRepository.findBy(INSURANCE_NUMBER);

        assertThat(result, isAbsent());
    }

    @Test
    public void findByInsuranceNumber_should_return_Insurance_when_it_exists() throws Exception {
        insuranceRule.create(INSURANCE);

        Optional<Insurance> result = insuranceRepository.findBy(INSURANCE_NUMBER);

        assertThat(result, hasValue(equalTo(INSURANCE)));
    }

    @Test
    public void findByCustomer_should_return_empty_list_when_Insurance_is_not_found() throws Exception {
        List<Insurance> result = insuranceRepository.findBy(CUSTOMER);

        assertThat(result, is(empty()));
    }

    @Test
    public void findByCustomer_should_return_Insurance_if_found() throws Exception {
        insuranceRule.create(INSURANCE);

        List<Insurance> result = insuranceRepository.findBy(CUSTOMER);

        assertThat(result, contains(INSURANCE));
    }

    @Test
    public void findByCustomer_should_return_multiple_Insurances_if_found() throws Exception {
        Insurance insuranceA = anInsurance()
                .withInsuranceNumber(InsuranceNumber.of(1L))
                .belongsTo(CUSTOMER)
                .build();

        Insurance insuranceB = anInsurance()
                .withInsuranceNumber(InsuranceNumber.of(2L))
                .belongsTo(CUSTOMER)
                .build();

        Insurance insuranceC = anInsurance()
                .withInsuranceNumber(InsuranceNumber.of(3L))
                .belongsTo(CUSTOMER)
                .build();

        insuranceRule.create(insuranceA);
        insuranceRule.create(insuranceB);
        insuranceRule.create(insuranceC);

        List<Insurance> result = insuranceRepository.findBy(CUSTOMER);

        assertThat(result, contains(insuranceA, insuranceB, insuranceC));
    }

    private void create(Insurance insurance) {
        Optional<Customer> customer = customerRepository.findBy(insurance.getCustomer().getPersonalIdentifier());
        if (!customer.isPresent()) {
            customerRepository.create(insurance.getCustomer());
        }

        insuranceRepository.create(insurance);
    }

    private static class InsuranceRule extends ExternalResource {

        private List<Insurance> createdInsurances = new ArrayList<>();

        private InsuranceRepository insuranceRepository;
        private CustomerRepository customerRepository;

        public void setInsuranceRepository(InsuranceRepository insuranceRepository) {
            this.insuranceRepository = insuranceRepository;
        }

        public void setCustomerRepository(CustomerRepository customerRepository) {
            this.customerRepository = customerRepository;
        }

        public void create(Insurance insurance) {
            Optional<Customer> customer = customerRepository.findBy(insurance.getCustomer().getPersonalIdentifier());
            if (!customer.isPresent()) {
                customerRepository.create(insurance.getCustomer());
            }
            insuranceRepository.create(insurance);
            createdInsurances.add(insurance);
        }

        @Override
        protected void after() {
            for (Insurance insurance : createdInsurances) {
                insuranceRepository.delete(insurance);

                if (insuranceRepository.findBy(insurance.getCustomer()).isEmpty()) {
                    customerRepository.delete(insurance.getCustomer());
                }
            }
        }
    }
}
