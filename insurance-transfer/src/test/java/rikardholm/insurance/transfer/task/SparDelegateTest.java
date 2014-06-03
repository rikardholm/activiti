package rikardholm.insurance.transfer.task;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.pvm.runtime.ExecutionImpl;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;
import rikardholm.insurance.domain.customer.Address;
import rikardholm.insurance.domain.customer.PersonalIdentifier;
import rikardholm.insurance.infrastructure.fake.FakeSparService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;

public class SparDelegateTest {

    public static final PersonalIdentifier PERSONAL_IDENTIFIER = PersonalIdentifier.of("341118-3028");
    public static final Address ADDRESS = Address.of("Spargränden 18, 345 64 Kristinehamn");

    private FakeSparService fakeSparService = new FakeSparService();
    private SparDelegate sparDelegate = new SparDelegate(fakeSparService);
    private final DelegateExecution delegateExecution = new ExecutionImpl();

    @Test
    public void should_add_Address_to_execution_when_found() throws Exception {
        fakeSparService.add(PERSONAL_IDENTIFIER, ADDRESS);

        sparDelegate.lookup(delegateExecution, PERSONAL_IDENTIFIER);

        assertThat(delegateExecution.getVariable("address"), isAnAddress(equalTo(ADDRESS)));
        assertThat(delegateExecution.getVariable("foundPerson"), isABooleanWithValue(true));
        assertThat(delegateExecution.getVariable("secretAddress"), isABooleanWithValue(false));
        assertThat(delegateExecution.getVariable("lookupSuccessful"), isABooleanWithValue(true));
    }

    @Test
    public void should_set_secretAddress_to_true_if_secret() throws Exception {
        fakeSparService.addSecret(PERSONAL_IDENTIFIER);

        sparDelegate.lookup(delegateExecution, PERSONAL_IDENTIFIER);

        assertThat(delegateExecution.getVariable("foundPerson"), isABooleanWithValue(true));
        assertThat(delegateExecution.getVariable("secretAddress"), isABooleanWithValue(true));
        assertThat(delegateExecution.getVariable("lookupSuccessful"), isABooleanWithValue(true));
    }

    @Test
    public void should_set_lookupSuccessful_to_false_if_service_throws_exception() throws Exception {
        fakeSparService.makeUnavailable();

        sparDelegate.lookup(delegateExecution, PERSONAL_IDENTIFIER);

        assertThat(delegateExecution.getVariable("foundPerson"), isABooleanWithValue(false));
        assertThat(delegateExecution.getVariable("secretAddress"), isABooleanWithValue(false));
        assertThat(delegateExecution.getVariable("lookupSuccessful"), isABooleanWithValue(false));
    }

    @Test
    public void should_set_foundPerson_to_false_if_not_found() throws Exception {
        sparDelegate.lookup(delegateExecution, PERSONAL_IDENTIFIER);

        assertThat(delegateExecution.getVariable("foundPerson"), isABooleanWithValue(false));
        assertThat(delegateExecution.getVariable("secretAddress"), isABooleanWithValue(false));
        assertThat(delegateExecution.getVariable("lookupSuccessful"), isABooleanWithValue(true));
    }

    private Matcher<? super Object> isABooleanWithValue(final Boolean value) {
        return new TypeSafeMatcher<Object>() {
            @Override
            protected boolean matchesSafely(Object item) {
                return item instanceof Boolean && item.equals(value);

            }

            @Override
            public void describeTo(Description description) {
                description.appendText("a boolean equaling ").appendValue(value);
            }
        };
    }

    private Matcher<? super Object> isAnAddress(final Matcher<Address> matcher) {
        return new TypeSafeMatcher<Object>() {
            @Override
            protected boolean matchesSafely(Object item) {
                return item instanceof Address && matcher.matches(item);
            }

            @Override
            public void describeTo(Description description) {
                  description.appendText("an Address ").appendDescriptionOf(matcher);
            }
        };
    }
}