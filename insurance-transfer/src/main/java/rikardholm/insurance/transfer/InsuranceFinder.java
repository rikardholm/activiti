package rikardholm.insurance.transfer;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import rikardholm.insurance.domain.*;

import java.util.List;

import static com.google.common.collect.Lists.transform;
import static java.util.Collections.emptyList;

public class InsuranceFinder {
    private CustomerRepository customerRepository;
    private InsuranceRepository insuranceRepository;

    public InsuranceFinder(CustomerRepository customerRepository, InsuranceRepository insuranceRepository) {
        this.customerRepository = customerRepository;
        this.insuranceRepository = insuranceRepository;
    }

    public List<InsuranceNumber> findByPersonnummer(PersonalIdentifier personnummer) {
        Optional<? extends Customer> customerOptional = customerRepository.findBy(personnummer);

        if (!customerOptional.isPresent()) {
            return emptyList();
        }

        List<Insurance> insurances = insuranceRepository.findBy(customerOptional.get());

        List<InsuranceNumber> insuranceNumbers = asInsuranceNumbers(insurances);

        return insuranceNumbers;
    }

    private List<InsuranceNumber> asInsuranceNumbers(List<Insurance> insurances) {
        return ImmutableList.copyOf(transform(insurances, toInsuranceNumber()));
    }

    private Function<? super Insurance, InsuranceNumber> toInsuranceNumber() {
        return new Function<Insurance, InsuranceNumber>() {
            @Override
            public InsuranceNumber apply(Insurance input) {
                return input.getInsuranceNumber();
            }
        };
    }
}
