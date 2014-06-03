package rikardholm.insurance.domain.common;

import java.io.Serializable;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public abstract class AbstractValueObject<VALUE extends Serializable> implements ValueObject<VALUE> {
    private static final long serialVersionUID = -2848533905709849199L;

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
