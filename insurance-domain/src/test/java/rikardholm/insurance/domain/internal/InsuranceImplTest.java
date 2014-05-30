package rikardholm.insurance.domain.internal;

import org.junit.Test;
import rikardholm.insurance.domain.customer.CustomerBuilder;
import rikardholm.insurance.domain.customer.Address;
import rikardholm.insurance.domain.customer.Customer;
import rikardholm.insurance.domain.customer.PersonalIdentifier;
import rikardholm.insurance.domain.insurance.Insurance;
import rikardholm.insurance.domain.insurance.InsuranceBuilder;
import rikardholm.insurance.domain.insurance.InsuranceNumber;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class InsuranceImplTest {

    public static final Customer CUSTOMER = CustomerBuilder.aCustomer()
            .withPersonalIdentifier(PersonalIdentifier.of("3455"))
            .withAddress(Address.of("Testlane 30"))
            .build();
    public static final InsuranceNumber INSURANCE_NUMBER = InsuranceNumber.of(45967L);
    public static final Insurance INSURANCE = InsuranceBuilder.anInsurance()
            .withInsuranceNumber(INSURANCE_NUMBER)
            .belongsTo(CUSTOMER)
            .build();

    @Test
    public void is_equal_if_InsuranceNumber_is_equal() throws Exception {

        Customer otherCustomer = CustomerBuilder.aCustomer()
                .withPersonalIdentifier(PersonalIdentifier.of("192339"))
                .withAddress(Address.of("Testbrook Ave. 14567"))
                .build();

        Insurance otherInsurance = InsuranceBuilder.anInsurance()
                .withInsuranceNumber(INSURANCE_NUMBER)
                .belongsTo(otherCustomer)
                .build();

        assertThat(INSURANCE, is(equalTo(otherInsurance)));
        assertThat(otherInsurance, is(equalTo(INSURANCE)));
        assertThat(INSURANCE.hashCode(), is(equalTo(otherInsurance.hashCode())));
    }

    @Test
    public void is_not_equal_if_InsuranceNumber_is_different() throws Exception {
         Insurance otherInsurance = InsuranceBuilder.anInsurance()
                 .withInsuranceNumber(InsuranceNumber.of(495866L))
                 .belongsTo(CUSTOMER)
                 .build();

        assertThat(INSURANCE, is(not(equalTo(otherInsurance))));
        assertThat(otherInsurance, is(not(equalTo(INSURANCE))));
    }
}