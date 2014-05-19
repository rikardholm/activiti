package rikardholm.insurance.domain.common;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public abstract class AbstractValueObject<VALUE> implements ValueObject<VALUE> {

    public final VALUE value;

    public AbstractValueObject(VALUE value) {
        this.value = requireNonNull(value);
    }

    @Override
    public VALUE getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof AbstractValueObject) {
          return Objects.equals(value, ((AbstractValueObject) o).value);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "{" + value + '}';
    }
}
