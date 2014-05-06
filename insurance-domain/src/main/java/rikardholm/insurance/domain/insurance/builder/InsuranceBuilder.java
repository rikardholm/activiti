package rikardholm.insurance.domain.insurance.builder;

import rikardholm.insurance.domain.insurance.Customer;
import rikardholm.insurance.domain.insurance.Insurance;
import rikardholm.insurance.domain.insurance.InsuranceNumber;
import rikardholm.insurance.domain.insurance.internal.InsuranceImpl;

import static java.util.Objects.requireNonNull;

public class InsuranceBuilder {
    private InsuranceBuilder() {}

    public static WithInsuranceNumber anInsurance() {
        return new InnerBuilder();
    }

    private static class InnerBuilder implements WithInsuranceNumber, BelongsTo, Builder<Insurance> {
        private InsuranceNumber insuranceNumber;
        private Customer customer;

        @Override
        public BelongsTo withInsuranceNumber(InsuranceNumber insuranceNumber) {
            this.insuranceNumber = requireNonNull(insuranceNumber);
            return this;
        }

        @Override
        public Builder<Insurance> belongsTo(Customer customer) {
            this.customer = requireNonNull(customer);
            return this;
        }

        @Override
        public Insurance build() {
            return new InsuranceImpl(insuranceNumber, customer);
        }
    }

    public interface WithInsuranceNumber {
        BelongsTo withInsuranceNumber(InsuranceNumber insuranceNumber);
    }

    public interface BelongsTo {
        Builder<Insurance> belongsTo(Customer customer);

    }
}
