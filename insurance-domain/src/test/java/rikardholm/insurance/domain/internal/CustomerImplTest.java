package rikardholm.insurance.domain.internal;

import org.junit.Test;
import rikardholm.insurance.domain.customer.Address;
import rikardholm.insurance.domain.customer.Customer;
import rikardholm.insurance.domain.customer.PersonalIdentifier;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CustomerImplTest {
    @Test(expected = NullPointerException.class)
    public void should_refuse_null_PersonalIdentifier() throws Exception {
         new CustomerImpl(null, Address.of("asdf"));
    }

    @Test(expected = NullPointerException.class)
    public void should_refuse_null_Address() throws Exception {
        new CustomerImpl(PersonalIdentifier.of("341209-3045"),null);
    }

    @Test
    public void equals_when_two_instances_have_equal_PersonalIdentifiers() throws Exception {
        String personalIdentifier = "112233-4455";
        PersonalIdentifier personalIdentifierA = PersonalIdentifier.of(personalIdentifier);
        PersonalIdentifier personalIdentifierB = PersonalIdentifier.of(personalIdentifier);

        Customer customerA = new CustomerImpl(personalIdentifierA, Address.of("asdfstreet"));
        Customer customerB = new CustomerImpl(personalIdentifierB, Address.of("Qwertyplan 12"));

        assertTheyAreEqual(customerA, customerB);
    }

    private void assertTheyAreEqual(Customer customerA, Customer customerB) {
        assertThat(customerA, equalTo(customerB));
        assertThat(customerB, equalTo(customerA));
        assertThat(customerA.hashCode(), equalTo(customerB.hashCode()));
    }
}
