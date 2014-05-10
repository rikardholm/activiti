package rikardholm.insurance.infrastructure.fake;

import com.google.common.base.Optional;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;
import rikardholm.insurance.application.spar.SparResult;
import rikardholm.insurance.application.spar.SparUnavailableException;
import rikardholm.insurance.domain.PersonalIdentifier;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static rikardholm.insurance.common.test.OptionalMatchers.hasValue;
import static rikardholm.insurance.common.test.OptionalMatchers.isAbsent;

public class FakeSparServiceTest {

    public static final String PERSONAL_IDENTIFIER_STRING = "45678";
    public static final PersonalIdentifier PERSONAL_IDENTIFIER = PersonalIdentifier.of(PERSONAL_IDENTIFIER_STRING);
    public static final String ADDRESS = "Aktergatan 11";

    private FakeSparService fakeSparService = new FakeSparService();
    private Optional<SparResult> result;

    @Test
    public void should_return_SparResult_if_entry_was_added() throws Exception {
        fakeSparService.add(PERSONAL_IDENTIFIER, ADDRESS);

        result = fakeSparService.findBy(PersonalIdentifier.of(PERSONAL_IDENTIFIER_STRING));

        assertThat(result, hasValue(sparResultFound(allOf(withPersonalIdentifier(PERSONAL_IDENTIFIER), withAddress(ADDRESS)))));
    }

    @Test
    public void should_return_absent_if_not_found() throws Exception {
        result = fakeSparService.findBy(PERSONAL_IDENTIFIER);

        assertThat(result, isAbsent());
    }

    @Test
    public void should_return_secret_if_secret_was_added() throws Exception {
        fakeSparService.addSecret(PERSONAL_IDENTIFIER);

        result = fakeSparService.findBy(PersonalIdentifier.of(PERSONAL_IDENTIFIER_STRING));

        assertThat(result, hasValue(withPersonalIdentifier(PERSONAL_IDENTIFIER)));
    }

    @Test(expected = SparUnavailableException.class)
    public void should_throw_exceptions_when_service_is_unavailable() throws Exception {
        fakeSparService.makeUnavailable();

        fakeSparService.findBy(PERSONAL_IDENTIFIER);
    }

    @Test
    public void should_be_able_to_make_service_available_again() throws Exception {
        fakeSparService.makeUnavailable();
        fakeSparService.makeAvailable();

        fakeSparService.findBy(PERSONAL_IDENTIFIER);
    }

    private Matcher<SparResult> withPersonalIdentifier(final PersonalIdentifier personalIdentifier) {
        return new TypeSafeMatcher<SparResult>() {
            @Override
            protected boolean matchesSafely(SparResult item) {
                return personalIdentifier.equals(item.personalIdentifier);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with Personal Identifier ")
                        .appendValue(personalIdentifier);
            }
        };
    }

    private Matcher<SparResult> sparResultFound(final Matcher<SparResult.Found> sparResultMatcher) {
        return new TypeSafeMatcher<SparResult>() {
            @Override
            protected boolean matchesSafely(SparResult item) {
                if (item instanceof SparResult.Found) {
                    return sparResultMatcher.matches(item);
                }

                return false;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("SparResult.Found ").appendDescriptionOf(sparResultMatcher);
            }
        };
    }

    private Matcher<SparResult.Found> withAddress(final String address) {
        return new TypeSafeMatcher<SparResult.Found>() {
            @Override
            protected boolean matchesSafely(SparResult.Found item) {
                return address.equals(item.address);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with address ").appendValue(address);
            }
        };
    }
}
