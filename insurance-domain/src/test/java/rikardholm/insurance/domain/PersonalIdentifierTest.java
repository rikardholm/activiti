package rikardholm.insurance.domain;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PersonalIdentifierTest {
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
