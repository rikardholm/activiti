package rikardholm.insurance.infrastructure.oracle;

import com.google.common.base.Optional;

import rikardholm.insurance.domain.Customer;
import rikardholm.insurance.domain.CustomerRepository;
import rikardholm.insurance.domain.PersonalIdentifier;
import rikardholm.insurance.domain.internal.CustomerImpl;

public class OracleCustomerRepository implements CustomerRepository {

	private CustomerMapper customerMapper;

	public OracleCustomerRepository(CustomerMapper customerMapper) {
		this.customerMapper = customerMapper;
	}

	@Override
	public Optional<? extends Customer> findBy(PersonalIdentifier personalIdentifier) {
		return Optional.fromNullable(customerMapper.findByPersonalIdentifier(personalIdentifier));
	}

	@Override
	public void create(Customer instance) {
		checkCustomerDoesNotExist(instance);
		customerMapper.insert(instance);
	}

	private void checkCustomerDoesNotExist(Customer instance) {
		PersonalIdentifier personalIdentifier = instance.getPersonalIdentifier();
		Optional<? extends Customer> existing = Optional.fromNullable(customerMapper.findByPersonalIdentifier(personalIdentifier));

		if (existing.isPresent()) {
			throw new IllegalArgumentException("Personal Identifier already exists: " + personalIdentifier);
		}
	}

	@Override
	public void delete(Customer instance) {
		customerMapper.delete(instance);
	}
}
