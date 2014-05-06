package rikardholm.insurance.domain;

public interface Repository<T> {
    void create(T instance);
}
