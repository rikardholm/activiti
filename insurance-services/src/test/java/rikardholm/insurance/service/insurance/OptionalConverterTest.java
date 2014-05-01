package rikardholm.insurance.service.insurance;

import org.junit.Test;
import rikardholm.insurance.service.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static rikardholm.insurance.service.matchers.OptionalMatchers.hasValue;
import static rikardholm.insurance.service.matchers.OptionalMatchers.isAbsent;

public class OptionalConverterTest {
    @Test
    public void should_return_a_value_if_original_has_value() throws Exception {
        String value = "some string";
        com.google.common.base.Optional<String> original = com.google.common.base.Optional.of(value);

        Optional<String> result = OptionalConverter.convert(original);

        assertThat(result, hasValue(equalTo(value)));
    }

    @Test
    public void should_return_absent_if_original_is_absent() throws Exception {
        com.google.common.base.Optional<String> original = com.google.common.base.Optional.absent();

        Optional<String> result = OptionalConverter.convert(original);

        assertThat(result, isAbsent());
    }
}
