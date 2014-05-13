package rikardholm.transfer.workflow;

import com.google.common.base.Optional;
import cucumber.api.java.sv.Givet;
import cucumber.api.java.sv.Och;
import cucumber.api.java.sv.Så;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import rikardholm.insurance.domain.*;
import rikardholm.insurance.domain.builder.CustomerBuilder;
import rikardholm.insurance.infrastructure.fake.FakeSparService;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static rikardholm.insurance.common.test.OptionalMatchers.isPresent;

@Component
@ContextConfiguration("classpath*:test/cucumber.xml")
public class InsuranceRegistrationSteps {

    public static final PersonalIdentifier PERSONAL_IDENTIFIER = PersonalIdentifier.of("561102-3048");
    public static final Customer CUSTOMER = CustomerBuilder.aCustomer()
            .withPersonalIdentifier(PERSONAL_IDENTIFIER)
            .build();

    public static final String EXISTERANDE_UPPGIFTER = "Existensplan 8, 42 666 Ingenstans";
    public static final String SPAR_UPPGIFTER = "SPARgatan 511, 120 66 Stockholm";
    public static final String MOS_UPPGIFTER = "MOgränd 234, 117 28 Stockholm";

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private FakeSparService fakeSparService;
    @Autowired
    private InsuranceRepository insuranceRepository;


    @Givet("^en person som är kund hos företaget")
    public void en_person_som_är_kund_hos_företaget() {
        customerRepository.create(CUSTOMER);
    }

    @Givet("^en person som inte är kund hos företaget")
    public void en_person_som_inte_är_kund_hos_företaget() {
        customerRepository.delete(CUSTOMER);
    }

    @Givet("^som finns i SPAR")
    public void som_finns_i_SPAR() {
        fakeSparService.add(PERSONAL_IDENTIFIER, SPAR_UPPGIFTER);
    }

    @Och("^som har skyddad identitet i SPAR")
    public void som_har_skyddad_identitet_i_SPAR() {
        fakeSparService.addSecret(PERSONAL_IDENTIFIER);
    }

    @Och("^att SPAR är nere")
    public void att_SPAR_är_nere() {
        fakeSparService.makeUnavailable();
    }

    @Så("^skapas en försäkring med kundens existerande uppgifter")
    public void skapas_en_försäkring_med_kundens_existerande_uppgifter() {
        List<Insurance> insurances = getInsurances(PERSONAL_IDENTIFIER);

        assertThat(insurances, contains(insuranceWithAddress(EXISTERANDE_UPPGIFTER)));
    }

    @Så("^skapas en försäkring med personens uppgifter i SPAR")
    public void skapas_en_försäkring_med_personens_uppgifter_i_SPAR() {
        List<Insurance> insurances = getInsurances(PERSONAL_IDENTIFIER);

        assertThat(insurances, contains(insuranceWithAddress(SPAR_UPPGIFTER)));
    }

    @Och("^försäkringen skapas med MOs uppgifter")
    public void försäkringen_skapas_med_MOs_uppgifter() {
        List<Insurance> insurances = getInsurances(PERSONAL_IDENTIFIER);

        assertThat(insurances, contains(insuranceWithAddress(MOS_UPPGIFTER)));
    }

    private List<Insurance> getInsurances(PersonalIdentifier personalIdentifier) {
        Optional<? extends Customer> customer = customerRepository.findBy(personalIdentifier);
        assertThat(customer, isPresent());
        return insuranceRepository.findBy(customer.get());
    }

    private Matcher<Insurance> insuranceWithAddress(final String uppgifter) {
        return new TypeSafeMatcher<Insurance>() {
            @Override
            protected boolean matchesSafely(Insurance item) {
                return uppgifter.equals(item.getAddress());
            }

            @Override
            public void describeTo(Description description) {
                 description.appendText("an insurance with address ").appendValue(uppgifter);
            }

            @Override
            protected void describeMismatchSafely(Insurance item, Description mismatchDescription) {
                mismatchDescription.appendText("the address was ").appendValue(item.getAddress());
            }
        };
    }


}
