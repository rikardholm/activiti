package rikardholm.insurance.domain.internal;

import rikardholm.insurance.domain.customer.Customer;
import rikardholm.insurance.domain.insurance.Insurance;
import rikardholm.insurance.domain.insurance.InsuranceBuilder;
import rikardholm.insurance.domain.insurance.InsuranceRegistration;
import rikardholm.insurance.domain.insurance.InsuranceRepository;

public class InsuranceRegistrationImpl implements InsuranceRegistration {
    private InsuranceRepository insuranceRepository;
    private InsuranceNumberGenerator insuranceNumberGenerator;

    public InsuranceRegistrationImpl(InsuranceRepository insuranceRepository, InsuranceNumberGenerator insuranceNumberGenerator) {
        this.insuranceRepository = insuranceRepository;
        this.insuranceNumberGenerator = insuranceNumberGenerator;
    }

    @Override
    public Insurance register(Customer customer) {
        Insurance insurance = InsuranceBuilder.anInsurance()
                .withInsuranceNumber(insuranceNumberGenerator.generate())
                .belongsTo(customer)
                .build();

        insuranceRepository.save(insurance);

        return insurance;
    }
}
