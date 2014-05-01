package rikardholm.insurance.service.matchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import rikardholm.insurance.service.Optional;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;

public class OptionalMatchers {
    public static Matcher<Optional<?>> isPresent() {
        return new TypeSafeMatcher<Optional<?>>() {
            @Override
            protected boolean matchesSafely(Optional<?> item) {
                return item.isPresent();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("value to be present");
            }

            @Override
            protected void describeMismatchSafely(Optional<?> item, Description mismatchDescription) {
                mismatchDescription.appendText("value was absent");
            }
        };
    }

    public static Matcher<Optional<?>> isAbsent() {
        return new TypeSafeMatcher<Optional<?>>() {
            @Override
            protected boolean matchesSafely(Optional<?> item) {
                return !item.isPresent();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("value to be absent");
            }

            @Override
            protected void describeMismatchSafely(Optional<?> item, Description mismatchDescription) {
                mismatchDescription.appendText("value was present: ").appendValue(item.getValue());
            }
        };
    }

    public static <T> Matcher<Optional<T>> hasValue(final Matcher<T> matcher) {
        return new TypeSafeMatcher<Optional<T>>() {
            @Override
            protected boolean matchesSafely(Optional<T> item) {
                if (!item.isPresent()) {
                    return false;
                }

                return matcher.matches(item.getValue());
            }

            @Override
            public void describeTo(Description description) {
                 description.appendText("has value ").appendDescriptionOf(matcher);
            }

            @Override
            protected void describeMismatchSafely(Optional<T> item, Description mismatchDescription) {
                if (!item.isPresent()) {
                    mismatchDescription.appendText("value is absent");
                    return;
                }

                mismatchDescription.appendText("value ");
                matcher.describeMismatch(item.getValue(), mismatchDescription);
            }
        };
    }

    public static <T> Matcher<Optional<T>> hasValue(final T value) {
        return hasValue(equalTo(value));
    }
}
