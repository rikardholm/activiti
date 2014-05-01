package rikardholm.insurance.service.insurance;

public interface Repository<T> {
    void create(T instance);
}
