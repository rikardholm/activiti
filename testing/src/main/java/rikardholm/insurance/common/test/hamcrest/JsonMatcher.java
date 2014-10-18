package rikardholm.insurance.common.test.hamcrest;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.io.IOException;

public class JsonMatcher {
    public static Matcher<String> isJson() {
        return new TypeSafeMatcher<String>() {
            @Override
            protected boolean matchesSafely(String item) {
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    objectMapper.readTree(item);
                } catch (IOException e) {
                    return false;
                }

                return true;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("is json");
            }

            @Override
            protected void describeMismatchSafely(String item, Description mismatchDescription) {
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    objectMapper.readTree(item);
                } catch (IOException e) {
                    mismatchDescription.appendText(item).appendText(" is not json. ").appendText(e.getMessage());
                }
            }
        };
    }

    public static Matcher<String> isJson(final Matcher<JsonNode> matcher) {
        return new TypeSafeMatcher<String>() {
            @Override
            protected boolean matchesSafely(String item) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode;
                try {
                    jsonNode = objectMapper.readTree(item);
                } catch (IOException e) {
                    return false;
                }

                return matcher.matches(jsonNode);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("is json ").appendDescriptionOf(matcher);
            }

            @Override
            protected void describeMismatchSafely(String item, Description mismatchDescription) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode;
                try {
                    jsonNode = objectMapper.readTree(item);
                } catch (IOException e) {
                    mismatchDescription.appendText(item).appendText(" is not json. ").appendText(e.getMessage());
                    return;
                }

                matcher.describeMismatch(jsonNode, mismatchDescription);
            }
        };
    }

    public static Matcher<JsonNode> withProperty(final String propertyName, final Matcher<JsonNode> matcher) {
        return new TypeSafeMatcher<JsonNode>() {
            @Override
            protected boolean matchesSafely(JsonNode item) {
                if (!item.has(propertyName)) {
                    return false;
                }

                JsonNode jsonNode = item.get(propertyName);

                return matcher.matches(jsonNode);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with property ")
                        .appendValue(propertyName)
                        .appendText(" ")
                        .appendDescriptionOf(matcher);
            }

            @Override
            protected void describeMismatchSafely(JsonNode item, Description mismatchDescription) {
                if (!item.has(propertyName)) {
                    mismatchDescription.appendText("does not have property ").appendValue(propertyName).appendText(". Json: ").appendValue(item);
                    return;
                }

                JsonNode jsonNode = item.get(propertyName);

                mismatchDescription.appendText("property ").appendValue(propertyName).appendText(" ");

                matcher.describeMismatch(jsonNode, mismatchDescription);
            }
        };
    }

    public static Matcher<JsonNode> equalTo(final String json) {
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readTree(json);
        } catch (IOException e) {
            throw new IllegalArgumentException("This is not valid json: " + json, e);
        }
        return new TypeSafeMatcher<JsonNode>() {
            @Override
            protected boolean matchesSafely(JsonNode item) {
                return jsonNode.equals(item);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("equal to ").appendValue(jsonNode);
            }

            @Override
            protected void describeMismatchSafely(JsonNode item, Description mismatchDescription) {
                mismatchDescription.appendText("has value ").appendValue(item);
            }
        };
    }
}
