package rikardholm.insurance.transfer;

import rikardholm.insurance.common.Optional;
import rikardholm.insurance.domain.PersonalIdentifier;
import rikardholm.insurance.domain.insurance.Customer;
import rikardholm.insurance.domain.insurance.CustomerRepository;
import rikardholm.insurance.domain.insurance.Insurance;
import rikardholm.insurance.domain.insurance.InsuranceRepository;

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
