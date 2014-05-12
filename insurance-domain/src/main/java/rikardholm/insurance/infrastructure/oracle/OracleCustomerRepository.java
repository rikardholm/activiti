package rikardholm.insurance.infrastructure.oracle;

import com.google.common.base.Optional;

import rikardholm.insurance.domain.Customer;
import rikardholm.insurance.domain.CustomerRepository;
import rikardholm.insurance.domain.PersonalIdentifier;

public class OracleCustomerRepository implements CustomerRepository {
	@Override
	public Optional<Customer> findBy(PersonalIdentifier personalIdentifier) {
		return null;
	}

	@Override
	public void create(Customer instance) {

	}

	@Override
	public void delete(Customer instance) {

	}
}
