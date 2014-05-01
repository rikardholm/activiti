package rikardholm.insurance.service.insurance;

public class InsuranceNumber {
    private final Long insuranceNumber;

    private InsuranceNumber(Long insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
    }

    public Long getInsuranceNumber() {
        return insuranceNumber;
    }

    @Override
    public String toString() {
        return "Insurance Number " + insuranceNumber;
    }

    public static InsuranceNumber of(Long value) {
        return new InsuranceNumber(value);
    }
}
