package rikardholm.insurance.domain.matchers;

import org.hamcrest.Matcher;
import rikardholm.insurance.common.test.AbstractPropertyMatcher;
import rikardholm.insurance.domain.customer.Address;
import rikardholm.insurance.domain.customer.PersonalIdentifier;
import rikardholm.insurance.domain.customer.Customer;

public class CustomerMatchers {
    public static Matcher<Customer> hasPersonalIdentifier(final Matcher<PersonalIdentifier> personalIdentifierMatcher) {
        return new AbstractPropertyMatcher<Customer, PersonalIdentifier>("personal identifier", personalIdentifierMatcher) {

            @Override
            protected PersonalIdentifier getValue(Customer item) {
                return item.getPersonalIdentifier();
            }
        };
    }

    public static Matcher<? super Customer> hasAddress(final Matcher<Address> addressMatcher) {
        return new AbstractPropertyMatcher<Customer, Address>("address", addressMatcher) {

            @Override
            protected Address getValue(Customer item) {
                return item.getAddress();
            }
        };
    }
}
