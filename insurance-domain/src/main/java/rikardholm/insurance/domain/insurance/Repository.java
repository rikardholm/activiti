package rikardholm.insurance.domain.insurance;

public interface Repository<T> {
    void create(T instance);
}
