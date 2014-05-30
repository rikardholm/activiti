package rikardholm.insurance.domain.internal;

import rikardholm.insurance.domain.customer.Customer;
import rikardholm.insurance.domain.insurance.Insurance;
import rikardholm.insurance.domain.insurance.InsuranceNumber;

import java.util.Objects;

public class InsuranceImpl implements Insurance {
    private final InsuranceNumber insuranceNumber;
    public final Customer customer;
	private Long id;

    public InsuranceImpl(InsuranceNumber insuranceNumber, Customer customer) {
        this.insuranceNumber = insuranceNumber;
        this.customer = customer;
    }

    public InsuranceImpl(InsuranceNumber insuranceNumber, CustomerImpl customer) {
        this(insuranceNumber, (Customer) customer);
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
    public Customer getCustomer() {
        return customer;
    }

    @Override
    public InsuranceNumber getInsuranceNumber() {
        return insuranceNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Insurance) {
            return Objects.equals(insuranceNumber, ((Insurance) o).getInsuranceNumber());
        }

        return false;
    }

    @Override
    public int hashCode() {
        return insuranceNumber.hashCode();
    }

    @Override
    public String toString() {
        return "Insurance {" + insuranceNumber +
                " of " + customer + "}";
    }
}
