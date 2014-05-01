package rikardholm.insurance.service.matchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.springframework.context.ApplicationContext;

import java.util.Map;

public class ApplicationContextMatchers {
    public static Matcher<ApplicationContext> hasExactlyOneBeanOfType(final Class<?> clazz) {
        return new TypeSafeMatcher<ApplicationContext>() {
            @Override
            protected boolean matchesSafely(ApplicationContext item) {
                Map<String, ?> beans = item.getBeansOfType(clazz);

                return beans.size() == 1;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("An ApplicationContext with exactly one ")
                        .appendValue(clazz)
                        .appendText(" bean");
            }

            @Override
            protected void describeMismatchSafely(ApplicationContext item, Description mismatchDescription) {
                Map<String, ?> beans = item.getBeansOfType(clazz);

                mismatchDescription.appendText("The ApplicationContext has ")
                        .appendValue(beans.size())
                        .appendText(" beans of type ")
                        .appendValue(clazz);
            }
        };
    }
}
