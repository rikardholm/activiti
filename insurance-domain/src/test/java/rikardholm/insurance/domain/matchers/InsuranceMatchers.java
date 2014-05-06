package rikardholm.insurance.domain.matchers;

import org.hamcrest.Matcher;
import rikardholm.insurance.common.test.AbstractPropertyMatcher;
import rikardholm.insurance.domain.insurance.Customer;
import rikardholm.insurance.domain.insurance.Insurance;
import rikardholm.insurance.domain.insurance.InsuranceNumber;

public class InsuranceMatchers {
    public static Matcher<Insurance> hasInsuranceNumber(final Matcher<InsuranceNumber> insuranceNumberMatcher) {
        return new AbstractPropertyMatcher<Insurance, InsuranceNumber>("insurance number", insuranceNumberMatcher) {

            @Override
            protected InsuranceNumber getValue(Insurance item) {
                return item.getInsuranceNumber();
            }
        };
    }

    public static Matcher<Insurance> hasCustomer(final Matcher<Customer> customerMatcher) {
        return new AbstractPropertyMatcher<Insurance, Customer>("customer", customerMatcher) {

            @Override
            protected Customer getValue(Insurance item) {
                return item.getCustomer();
            }
        };
    }
}
