package rikardholm.insurance.service;

import static java.util.Objects.requireNonNull;

public class Optional<T> {
    private final T value;

    private Optional(T value) {
        this.value = requireNonNull(value);
    }

    public Optional() {
        this.value = null;
    }

    public T getValue() {
        if (value == null) {
            throw new IllegalStateException("Value is absent.");
        }

        return value;
    }

    public boolean isPresent() {
        return value != null;
    }

    public static <T> Optional<T> of(T value) {
        return new Optional<T>(value);
    }

    public static <T> Optional<T> absent() {
        return new Optional<T>();
    }
}
