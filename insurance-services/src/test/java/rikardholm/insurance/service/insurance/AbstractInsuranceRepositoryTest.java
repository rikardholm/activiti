package rikardholm.insurance.service.insurance;

import org.junit.Test;
import rikardholm.insurance.common.Optional;
import rikardholm.insurance.service.PersonalIdentifier;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static rikardholm.insurance.common.test.OptionalMatchers.hasValue;
import static rikardholm.insurance.common.test.OptionalMatchers.isAbsent;
import static rikardholm.insurance.service.insurance.Builders.aCustomer;
import static rikardholm.insurance.service.insurance.Builders.anInsurance;

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

    private InsuranceRepository insuranceRepository = getInstance();

    @Test
    public void findByInsuranceNumber_should_return_absent_when_Insurance_does_not_exist() {
        Optional<Insurance> result = insuranceRepository.findBy(INSURANCE_NUMBER);

        assertThat(result, isAbsent());
    }

    @Test
    public void findByInsuranceNumber_should_return_Insurance_when_it_exists() throws Exception {
        insuranceRepository.create(INSURANCE);

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
        insuranceRepository.create(INSURANCE);

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

        insuranceRepository.create(insuranceA);
        insuranceRepository.create(insuranceB);
        insuranceRepository.create(insuranceC);

        List<Insurance> result = insuranceRepository.findBy(CUSTOMER);

        assertThat(result, contains(insuranceA, insuranceB, insuranceC));
    }
}
