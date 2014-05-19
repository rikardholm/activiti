package rikardholm.insurance.domain;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class InsuranceNumberTest {
    @Test
    public void should_be_equal_if_representing_the_same_insurance_number() throws Exception {
        InsuranceNumber a = InsuranceNumber.of(456934L);
        InsuranceNumber b = InsuranceNumber.of(456934L);
        InsuranceNumber c = InsuranceNumber.of(1234L);

        assertThat("equals", a, equalTo(b));
        assertThat("equals", b, equalTo(a));
        assertThat("equals", a, not(equalTo(c)));
        assertThat("hashcode", a.hashCode(), equalTo(b.hashCode()));
    }
}
