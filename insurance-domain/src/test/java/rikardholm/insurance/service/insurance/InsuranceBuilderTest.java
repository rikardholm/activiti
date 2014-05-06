package rikardholm.insurance.service.insurance;

import org.junit.Test;
import rikardholm.insurance.service.PersonalIdentifier;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static rikardholm.insurance.service.insurance.builder.CustomerBuilder.aCustomer;
import static rikardholm.insurance.service.insurance.Builders.anInsurance;
import static rikardholm.insurance.service.matchers.InsuranceMatchers.hasCustomer;
import static rikardholm.insurance.service.matchers.InsuranceMatchers.hasInsuranceNumber;

public class InsuranceBuilderTest {
    private static final InsuranceNumber INSURANCE_NUMBER = InsuranceNumber.of(600134L);
    private static final Customer CUSTOMER = aCustomer()
            .withPersonalIdentifier(PersonalIdentifier.of("880415-3028"))
            .build();
    private static final Insurance INSURANCE = anInsurance()
            .withInsuranceNumber(INSURANCE_NUMBER)
            .belongsTo(CUSTOMER)
            .build();


    @Test
    public void builds_an_insurance() throws Exception {
        assertThat(INSURANCE, notNullValue());
    }

    @Test
    public void sets_Customer() throws Exception {
        assertThat(INSURANCE, hasCustomer(equalTo(CUSTOMER)));
    }

    @Test
    public void sets_insurance_number() throws Exception {
        assertThat(INSURANCE, hasInsuranceNumber(equalTo(INSURANCE_NUMBER)));
    }

    @Test(expected = NullPointerException.class)
    public void refuses_null_InsuranceNumber() throws Exception {
        anInsurance().withInsuranceNumber(null);
    }

    @Test(expected = NullPointerException.class)
    public void refuses_null_Customer() {
        anInsurance()
                .withInsuranceNumber(INSURANCE_NUMBER)
                .belongsTo(null);
    }
}
