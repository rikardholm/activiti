package rikardholm.insurance.domain.customer;

import org.junit.Test;
import rikardholm.insurance.domain.customer.PersonalIdentifier;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class PersonalIdentifierTest {

    @Test
    public void contains_the_set_value() {
        String expected = "asdf2345";
        PersonalIdentifier personalIdentifier = PersonalIdentifier.of(expected);

        assertThat(expected, is(equalTo(personalIdentifier.getValue())));
    }

    @Test(expected = NullPointerException.class)
    public void refuses_null_personalIdentifier() throws Exception {
        PersonalIdentifier.of(null);
    }

    @Test
    public void equals_if_two_instances_contain_same_personal_identifier() throws Exception {
        String personalIdentifier = "4567";
        PersonalIdentifier a = PersonalIdentifier.of(personalIdentifier);
        PersonalIdentifier b = PersonalIdentifier.of(personalIdentifier);

        assertThat("equals", a, equalTo(b));
        assertThat("equals", b, equalTo(a));
        assertThat("hashcode", a.hashCode(), equalTo(b.hashCode()));
    }
}
