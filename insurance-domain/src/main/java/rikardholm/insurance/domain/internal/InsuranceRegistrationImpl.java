package rikardholm.insurance.domain.internal;

import rikardholm.insurance.domain.customer.Customer;
import rikardholm.insurance.domain.insurance.*;

public class InsuranceRegistrationImpl implements InsuranceRegistration {
    private InsuranceRepository insuranceRepository;

    public InsuranceRegistrationImpl(InsuranceRepository insuranceRepository) {
        this.insuranceRepository = insuranceRepository;
    }

    @Override
    public Insurance register(Customer customer) {
        Insurance insurance = InsuranceBuilder.anInsurance()
                .withInsuranceNumber(InsuranceNumber.of(123L))
                .belongsTo(customer)
                .build();

        insuranceRepository.save(insurance);

        return insurance;
    }
}
