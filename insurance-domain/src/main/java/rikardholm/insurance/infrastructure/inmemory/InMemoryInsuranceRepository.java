package rikardholm.insurance.infrastructure.inmemory;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import rikardholm.insurance.domain.Customer;
import rikardholm.insurance.domain.Insurance;
import rikardholm.insurance.domain.InsuranceNumber;
import rikardholm.insurance.domain.InsuranceRepository;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Iterables.tryFind;
import static com.google.common.collect.Lists.newArrayList;

public class InMemoryInsuranceRepository implements InsuranceRepository {

    private List<Insurance> insurances = new ArrayList<Insurance>();

    @Override
    public void create(Insurance insurance) {
        insurances.add(insurance);
    }

    @Override
    public void delete(Insurance instance) {
        Optional<Insurance> insurance = findBy(instance.getInsuranceNumber());

        if (insurance.isPresent()) {
            insurances.remove(insurance.get());
        }
    }

    @Override
    public Optional<Insurance> findBy(final InsuranceNumber insuranceNumber) {
        return tryFind(insurances, new Predicate<Insurance>() {
            @Override
            public boolean apply(Insurance input) {
                return insuranceNumber.equals(input.getInsuranceNumber());
            }
        });
    }

    @Override
    public List<Insurance> findBy(final Customer customer) {
        return newArrayList(filter(insurances, new Predicate<Insurance>() {
            @Override
            public boolean apply(Insurance input) {
                return customer.equals(input.getCustomer());
            }
        }));
    }
}
