package rikardholm.insurance.domain.builder;

import org.junit.Test;
import rikardholm.insurance.domain.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static rikardholm.insurance.domain.builder.CustomerBuilder.aCustomer;
import static rikardholm.insurance.domain.matchers.InsuranceMatchers.hasCustomer;
import static rikardholm.insurance.domain.matchers.InsuranceMatchers.hasInsuranceNumber;

public class InsuranceBuilderTest {
    private static final InsuranceNumber INSURANCE_NUMBER = InsuranceNumber.of(600134L);
    private static final Customer CUSTOMER = aCustomer()
            .withPersonalIdentifier(PersonalIdentifier.of("880415-3028"))
            .withAddress(Address.of("Testallén 67")).build();
    private static final Insurance INSURANCE = InsuranceBuilder.anInsurance()
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
        InsuranceBuilder.anInsurance().withInsuranceNumber(null);
    }

    @Test(expected = NullPointerException.class)
    public void refuses_null_Customer() {
        InsuranceBuilder.anInsurance()
                .withInsuranceNumber(INSURANCE_NUMBER)
                .belongsTo(null);
    }
}
