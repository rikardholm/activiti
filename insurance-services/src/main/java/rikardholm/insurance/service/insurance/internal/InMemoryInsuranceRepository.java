package rikardholm.insurance.service.insurance.internal;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import rikardholm.insurance.common.Optional;
import rikardholm.insurance.common.OptionalConverter;
import rikardholm.insurance.service.insurance.Customer;
import rikardholm.insurance.service.insurance.Insurance;
import rikardholm.insurance.service.insurance.InsuranceNumber;
import rikardholm.insurance.service.insurance.InsuranceRepository;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Lists.newArrayList;

public class InMemoryInsuranceRepository implements InsuranceRepository {

    private List<Insurance> insurances = new ArrayList<Insurance>();

    @Override
    public void create(Insurance insurance) {
        insurances.add(insurance);
    }

    @Override
    public Optional<Insurance> findBy(final InsuranceNumber insuranceNumber) {
        return OptionalConverter.convert(Iterables.tryFind(insurances, new Predicate<Insurance>() {
            @Override
            public boolean apply(Insurance input) {
                return insuranceNumber.equals(input.getInsuranceNumber());
            }
        }));
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
