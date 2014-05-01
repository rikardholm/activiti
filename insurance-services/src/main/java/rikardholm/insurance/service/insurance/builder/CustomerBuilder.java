package rikardholm.insurance.service.insurance.builder;

import rikardholm.insurance.service.insurance.Customer;
import rikardholm.insurance.service.PersonalIdentifier;
import rikardholm.insurance.service.insurance.internal.CustomerImpl;

import static java.util.Objects.requireNonNull;

public class CustomerBuilder {
    private CustomerBuilder() {}

    public static WithPersonalIdentifier aCustomer() {
        return new InnerBuilder();
    }

    public static class InnerBuilder implements WithPersonalIdentifier, Builder<Customer> {

        private PersonalIdentifier personalIdentifier;

        private InnerBuilder() {
        }

        @Override
        public Builder<Customer> withPersonalIdentifier(PersonalIdentifier personalIdentifier) {
            this.personalIdentifier = requireNonNull(personalIdentifier);
            return this;
        }

        public Customer build() {
            return new CustomerImpl(personalIdentifier);
        }

    }

    public interface WithPersonalIdentifier {
        Builder<Customer> withPersonalIdentifier(PersonalIdentifier personalIdentifier);
    }
}
