package rikardholm.insurance.domain.internal;

import rikardholm.insurance.domain.insurance.InsuranceNumber;

import java.util.Random;

public class InsuranceNumberGenerator {

    public InsuranceNumber generate() {
        Random random = new Random();
        return InsuranceNumber.of(random.nextLong());
    }
}
