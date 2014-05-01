package rikardholm.insurance.service.insurance;

import rikardholm.insurance.service.Optional;

public class OptionalConverter {
    private OptionalConverter() {
    }

    public static <T> Optional<T> convert(com.google.common.base.Optional<T> original) {
        if(original.isPresent()) {
            return Optional.of(original.get());
        }

        return Optional.absent();
    }
}
