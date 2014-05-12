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
		Customer result = customerMapper.findByPersonalIdentifier(personalIdentifier);

		if (result == null) {
			return Optional.absent();
		}

		Customer customer = new CustomerImpl(personalIdentifier);
		return Optional.of(customer);
	}

	@Override
	public void create(Customer instance) {
		PersonalIdentifier personalIdentifier = instance.getPersonalIdentifier();
		Customer result = customerMapper.findByPersonalIdentifier(personalIdentifier);

		if (result != null) {
			throw new IllegalArgumentException("Personal Identifier already exists: " + personalIdentifier);
		}
		customerMapper.insert(personalIdentifier);
	}

	@Override
	public void delete(Customer instance) {
		customerMapper.delete(instance.getPersonalIdentifier());
	}
}
