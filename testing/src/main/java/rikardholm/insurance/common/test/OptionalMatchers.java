package rikardholm.insurance.common.test;

import com.google.common.base.Optional;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static org.hamcrest.CoreMatchers.equalTo;

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
                mismatchDescription.appendText("value was present: ").appendValue(item.get());
            }
        };
    }

    public static <T> Matcher<Optional<? extends T>> hasValue(final Matcher<T> matcher) {
        return new TypeSafeMatcher<Optional<? extends T>>() {
            @Override
            protected boolean matchesSafely(Optional<? extends T> item) {
                if (!item.isPresent()) {
                    return false;
                }

                return matcher.matches(item.get());
            }

            @Override
            public void describeTo(Description description) {
                 description.appendText("has value ").appendDescriptionOf(matcher);
            }

            @Override
            protected void describeMismatchSafely(Optional<? extends T> item, Description mismatchDescription) {
                if (!item.isPresent()) {
                    mismatchDescription.appendText("value is absent");
                    return;
                }

                mismatchDescription.appendText("value ");
                matcher.describeMismatch(item.get(), mismatchDescription);
            }
        };
    }

    public static <T> Matcher<Optional<? extends T>> hasValue(final T value) {
        return hasValue(equalTo(value));
    }
}
