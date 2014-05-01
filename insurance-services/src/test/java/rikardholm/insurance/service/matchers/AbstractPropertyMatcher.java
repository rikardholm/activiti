package rikardholm.insurance.service.matchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public abstract class AbstractPropertyMatcher<TYPE,PROPERTY> extends TypeSafeMatcher<TYPE> {

    private String propertyName;
    private Matcher<PROPERTY> matcher;

    protected abstract PROPERTY getValue(TYPE item);

    protected AbstractPropertyMatcher(String propertyName, Matcher<PROPERTY> matcher) {
        this.propertyName = propertyName;
        this.matcher = matcher;
    }

    @Override
    protected boolean matchesSafely(TYPE item) {
        return matcher.matches(getValue(item));
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("has ")
                .appendText(propertyName)
                .appendText(" ")
                .appendDescriptionOf(matcher);
    }

    @Override
    protected void describeMismatchSafely(TYPE item, Description mismatchDescription) {
        mismatchDescription
                .appendText(propertyName)
                .appendText(" ");

        matcher.describeMismatch(getValue(item), mismatchDescription);
    }
}
