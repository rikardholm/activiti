package rikardholm.insurance.domain.builder;

import rikardholm.insurance.domain.Customer;
import rikardholm.insurance.domain.PersonalIdentifier;
import rikardholm.insurance.domain.internal.CustomerImpl;

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
