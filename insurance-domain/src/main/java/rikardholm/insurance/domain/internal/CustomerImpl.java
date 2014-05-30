package rikardholm.insurance.domain.internal;

import rikardholm.insurance.domain.customer.Address;
import rikardholm.insurance.domain.customer.Customer;
import rikardholm.insurance.domain.customer.PersonalIdentifier;

import static java.util.Objects.requireNonNull;

public class CustomerImpl implements Customer {
    private final PersonalIdentifier personalIdentifier;
    private final Address address;
	private Long id;


    public CustomerImpl(PersonalIdentifier personalIdentifier, Address address) {
        this.address = requireNonNull(address);
        this.personalIdentifier = requireNonNull(personalIdentifier);
    }

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	@Override
    public PersonalIdentifier getPersonalIdentifier() {
        return personalIdentifier;
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "Customer " + personalIdentifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomerImpl customer = (CustomerImpl) o;

        if (!personalIdentifier.equals(customer.personalIdentifier)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return personalIdentifier.hashCode();
    }
}
