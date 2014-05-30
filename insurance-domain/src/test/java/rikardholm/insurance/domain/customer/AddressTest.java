package rikardholm.insurance.domain.customer;

import org.junit.Test;
import rikardholm.insurance.domain.customer.Address;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class AddressTest {
    @Test
    public void equals_test() throws Exception {
        Address a = Address.of("asdf123");
        Address b = Address.of("asdf123");
        Address c = Address.of("qwerty");

        assertThat(a, is(equalTo(b)));
        assertThat(b, is(equalTo(a)));
        assertThat(c, is(not(equalTo(a))));

        assertThat(a.hashCode(), is(equalTo(b.hashCode())));
        assertThat(c.hashCode(), is(not(equalTo(a.hashCode()))));
    }
}