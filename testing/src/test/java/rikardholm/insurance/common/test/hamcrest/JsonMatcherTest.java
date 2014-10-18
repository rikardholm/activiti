package rikardholm.insurance.common.test.hamcrest;

import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static rikardholm.insurance.common.test.hamcrest.JsonMatcher.equalTo;
import static rikardholm.insurance.common.test.hamcrest.JsonMatcher.isJson;
import static rikardholm.insurance.common.test.hamcrest.JsonMatcher.withProperty;

/**
 * Used to test json matchers on failure. Should be ignored normally.
 */
@Ignore
public class JsonMatcherTest {
    @Test
    public void not_json() throws Exception {
        assertThat("<not json>", isJson());
    }

    @Test
    public void does_not_have_property() throws Exception {
        assertThat("{\"ocd\":123}", isJson(withProperty("asdf", equalTo("234"))));
    }

    @Test
    public void does_not_have_property_equal_to() throws Exception {
        assertThat("{\"ocr\": 66667778}", isJson(withProperty("ocr", equalTo("66667777"))));
    }

    @Test
    public void matches() throws Exception {
        assertThat("{\"ocr\": 66667777}", isJson(withProperty("ocr", equalTo("66667777"))));
    }

    @Test
    public void complex() throws Exception {
        assertThat("{\"name\": \"jason\", \"address\": {\"street\":\"jsongatan 11\", \"postalcode\":12066}}", isJson(withProperty("address", equalTo("{\"street\":\"jsongatan 11\", \"postalcode\":12066}"))));
    }

    @Test
    public void complex_mismatch() throws Exception {
        assertThat("{\"name\": \"jason\", \"address\": {\"street\":\"jsongatan 12\", \"postalcode\":12066}}", isJson(withProperty("address", equalTo("{\"street\":\"jsongatan 11\", \"postalcode\":12066}"))));
    }
}