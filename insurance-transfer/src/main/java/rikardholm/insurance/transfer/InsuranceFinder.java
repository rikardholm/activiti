package rikardholm.insurance.transfer;

import rikardholm.insurance.common.Optional;
import rikardholm.insurance.domain.PersonalIdentifier;
import rikardholm.insurance.domain.Customer;
import rikardholm.insurance.domain.CustomerRepository;
import rikardholm.insurance.domain.Insurance;
import rikardholm.insurance.domain.InsuranceRepository;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

public class InsuranceFinder {
    private CustomerRepository customerRepository;
    private InsuranceRepository insuranceRepository;

    public InsuranceFinder(CustomerRepository customerRepository, InsuranceRepository insuranceRepository) {
        this.customerRepository = customerRepository;
        this.insuranceRepository = insuranceRepository;
    }

    public List<InsuranceInformation> findByPersonnummer(String personnummer) {
        Optional<Customer> customerOptional = customerRepository.findBy(PersonalIdentifier.of(personnummer));

        if (!customerOptional.isPresent()) {
            return emptyList();
        }

        List<Insurance> insurances = insuranceRepository.findBy(customerOptional.getValue());

        List<InsuranceInformation> insuranceInformations = new ArrayList<>();
        for (Insurance insurance : insurances) {
            insuranceInformations.add(new InsuranceInformation(insurance.getInsuranceNumber()));
        }

        return insuranceInformations;
    }
}
