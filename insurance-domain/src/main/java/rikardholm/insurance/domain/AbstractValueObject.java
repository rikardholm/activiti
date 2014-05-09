package rikardholm.insurance.domain;

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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractValueObject that = (AbstractValueObject) o;

        if (!value.equals(that.value)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return "{" + value + '}';
    }
}
