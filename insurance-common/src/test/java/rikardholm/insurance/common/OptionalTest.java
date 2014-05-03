package rikardholm.insurance.common;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class OptionalTest {
    public static final String VALUE = "asdf";
    private Optional<String> optional = Optional.of(VALUE);
    private Optional<String> absent = Optional.absent();

    @Test
    public void should_return_a_value_when_it_gets_one() throws Exception {
        assertThat(optional.getValue(), is(equalTo(VALUE)));
    }

    @Test
    public void isPresent_should_return_true_when_value_is_present() throws Exception {
        assertThat(optional.isPresent(), is(true));
    }

    @Test
    public void isPresent_should_return_false_when_value_is_absent() throws Exception {
        assertThat(absent.isPresent(), is(false));
    }

    @Test(expected = IllegalStateException.class)
    public void should_throw_exception_if_getValue_is_called_when_value_is_absent() throws Exception {
        absent.getValue();
    }

    @Test(expected = NullPointerException.class)
    public void of_should_not_accept_null() throws Exception {
        Optional.of(null);
    }
}
