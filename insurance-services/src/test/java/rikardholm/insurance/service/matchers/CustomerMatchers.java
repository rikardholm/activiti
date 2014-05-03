package rikardholm.insurance.service.matchers;

import org.hamcrest.Matcher;
import rikardholm.insurance.common.test.AbstractPropertyMatcher;
import rikardholm.insurance.service.PersonalIdentifier;
import rikardholm.insurance.service.insurance.Customer;

public class CustomerMatchers {
    public static Matcher<Customer> hasPersonalIdentifier(final Matcher<PersonalIdentifier> personalIdentifierMatcher) {
        return new AbstractPropertyMatcher<Customer, PersonalIdentifier>("personal identifier", personalIdentifierMatcher) {

            @Override
            protected PersonalIdentifier getValue(Customer item) {
                return item.getPersonalIdentifier();
            }
        };
    }
}
