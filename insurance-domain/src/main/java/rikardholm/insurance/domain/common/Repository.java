package rikardholm.insurance.domain.common;

public interface Repository<T> {
    void save(T instance);
    void delete(T instance);
}
