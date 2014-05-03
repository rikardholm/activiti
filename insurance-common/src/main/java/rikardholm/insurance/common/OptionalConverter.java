package rikardholm.insurance.common;

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
