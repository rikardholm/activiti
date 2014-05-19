package rikardholm.insurance.domain.builder;

import rikardholm.insurance.domain.Address;
import rikardholm.insurance.domain.Customer;
import rikardholm.insurance.domain.PersonalIdentifier;
import rikardholm.insurance.domain.internal.CustomerImpl;

import static java.util.Objects.requireNonNull;

public class CustomerBuilder {
    private CustomerBuilder() {
    }

    public static WithPersonalIdentifier aCustomer() {
        return new InnerBuilder();
    }

    public static class InnerBuilder implements WithPersonalIdentifier, WithAddress, Builder<Customer> {

        private PersonalIdentifier personalIdentifier;
        private Address address;

        private InnerBuilder() {
        }

        @Override
        public WithAddress withPersonalIdentifier(PersonalIdentifier personalIdentifier) {
            this.personalIdentifier = requireNonNull(personalIdentifier);
            return this;
        }

        public Customer build() {
            return new CustomerImpl(personalIdentifier, address);
        }

        @Override
        public Builder<Customer> withAddress(Address address) {
            this.address = requireNonNull(address);
            return this;
        }
    }

    public interface WithPersonalIdentifier {
        WithAddress withPersonalIdentifier(PersonalIdentifier personalIdentifier);
    }

    public interface WithAddress {
        Builder<Customer> withAddress(Address address);
    }
}
