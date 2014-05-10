package rikardholm.insurance.domain.common;

public interface Repository<T> {
    void create(T instance);
    void delete(T instance);
}
