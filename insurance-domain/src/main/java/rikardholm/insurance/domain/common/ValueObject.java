package rikardholm.insurance.domain.common;

import java.io.Serializable;

public interface ValueObject<TYPE extends Serializable> extends Serializable {
    TYPE getValue();
}
