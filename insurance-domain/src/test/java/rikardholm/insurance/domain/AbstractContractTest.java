package rikardholm.insurance.domain;

import org.junit.Before;

public abstract class AbstractContractTest<T> {
    protected T target;

    protected abstract T getInstance();

    @Before
    public void setUp() throws Exception {
        target = getInstance();
    }
}
