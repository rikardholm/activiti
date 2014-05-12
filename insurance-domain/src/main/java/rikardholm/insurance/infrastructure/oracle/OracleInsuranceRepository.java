package rikardholm.insurance.infrastructure.oracle;

import com.google.common.base.Optional;

import rikardholm.insurance.domain.Customer;
import rikardholm.insurance.domain.CustomerRepository;
import rikardholm.insurance.domain.PersonalIdentifier;

public class OracleInsuranceRepository implements CustomerRepository {
	@Override
	public Optional<Customer> findBy(PersonalIdentifier personalIdentifier) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void create(Customer instance) {
		 throw new UnsupportedOperationException();
	}

	@Override
	public void delete(Customer instance) {
		throw new UnsupportedOperationException();
	}
}
