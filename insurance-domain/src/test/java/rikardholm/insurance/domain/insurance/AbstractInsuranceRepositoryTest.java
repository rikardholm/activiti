package rikardholm.insurance.domain.insurance;

import com.google.common.base.Optional;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExternalResource;
import rikardholm.insurance.domain.AbstractContractTest;
import rikardholm.insurance.domain.customer.*;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static rikardholm.insurance.common.test.hamcrest.OptionalMatchers.hasValue;
import static rikardholm.insurance.common.test.hamcrest.OptionalMatchers.isAbsent;

public abstract class AbstractInsuranceRepositoryTest extends AbstractContractTest<InsuranceRepository> {

    private static final InsuranceNumber INSURANCE_NUMBER = InsuranceNumber.of(311768L);

    public static final Customer CUSTOMER = CustomerBuilder.aCustomer()
            .withPersonalIdentifier(PersonalIdentifier.of("840325-9043"))
            .withAddress(Address.of("Teststreet 129L"))
            .build();

    private static final Insurance INSURANCE = InsuranceBuilder.anInsurance()
            .withInsuranceNumber(INSURANCE_NUMBER)
            .belongsTo(CUSTOMER)
            .build();

    //@Rule
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
        Optional<? extends Insurance> result = insuranceRepository.findBy(INSURANCE_NUMBER);

        assertThat(result, isAbsent());
    }

    @Test
    public void findByInsuranceNumber_should_return_Insurance_when_it_exists() throws Exception {
        insuranceRule.create(INSURANCE);

        Optional<? extends Insurance> result = insuranceRepository.findBy(INSURANCE_NUMBER);

        assertThat(result, hasValue(equalTo(INSURANCE)));
    }

    @Test
    public void findByCustomer_should_return_empty_list_when_Insurance_is_not_found() throws Exception {
        List<? extends Insurance> existing = insuranceRepository.findBy(CUSTOMER);
        for (Insurance insurance : existing) {
            insuranceRepository.delete(insurance);
        }

        List<? extends Insurance> result = insuranceRepository.findBy(CUSTOMER);

        assertThat(result, is(empty()));
    }

    @Test
    public void findByCustomer_should_return_Insurance_if_found() throws Exception {
        insuranceRule.create(INSURANCE);

        List<? extends Insurance> result = insuranceRepository.findBy(CUSTOMER);

        assertThat(result, contains(INSURANCE));
    }

    @Test
    public void findByCustomer_should_return_multiple_Insurances_if_found() throws Exception {
        Insurance insuranceA = InsuranceBuilder.anInsurance()
                .withInsuranceNumber(InsuranceNumber.of(1L))
                .belongsTo(CUSTOMER)
                .build();

        Insurance insuranceB = InsuranceBuilder.anInsurance()
                .withInsuranceNumber(InsuranceNumber.of(2L))
                .belongsTo(CUSTOMER)
                .build();

        Insurance insuranceC = InsuranceBuilder.anInsurance()
                .withInsuranceNumber(InsuranceNumber.of(3L))
                .belongsTo(CUSTOMER)
                .build();

        insuranceRule.create(insuranceA);
        insuranceRule.create(insuranceB);
        insuranceRule.create(insuranceC);

        List<? extends Insurance> result = insuranceRepository.findBy(CUSTOMER);

        assertThat(result, containsInAnyOrder(insuranceA, insuranceB, insuranceC));
    }

    @Test
    public void should_delete_insurances_by_InsuranceNumber() throws Exception {
        insuranceRule.create(INSURANCE);

        Insurance sameEntity = InsuranceBuilder.anInsurance()
                .withInsuranceNumber(INSURANCE.getInsuranceNumber())
                .belongsTo(CUSTOMER)
                .build();
        insuranceRepository.delete(sameEntity);

        Optional<? extends Insurance> insurance = insuranceRepository.findBy(INSURANCE.getInsuranceNumber());

        assertThat(insurance, isAbsent());
    }

    @Test(expected = IllegalArgumentException.class)
    public void InsuranceNumbers_should_be_unique() throws Exception {
        insuranceRule.create(INSURANCE);

        Insurance sameInsuranceNumber = InsuranceBuilder.anInsurance()
                .withInsuranceNumber(INSURANCE.getInsuranceNumber())
                .belongsTo(CUSTOMER)
                .build();

        insuranceRepository.save(sameInsuranceNumber);
    }

    private void create(Insurance insurance) {
        Optional<? extends Customer> customer = customerRepository.findBy(insurance.getCustomer().getPersonalIdentifier());
        if (!customer.isPresent()) {
            customerRepository.save(insurance.getCustomer());
        }

        insuranceRepository.save(insurance);
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
            Optional<? extends Customer> customer = customerRepository.findBy(insurance.getCustomer().getPersonalIdentifier());
            if (!customer.isPresent()) {
                customerRepository.save(insurance.getCustomer());
            }
            insuranceRepository.save(insurance);
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
