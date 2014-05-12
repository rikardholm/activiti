package rikardholm.insurance.infrastructure.oracle;

import java.util.List;

import com.google.common.base.Optional;

import rikardholm.insurance.domain.Customer;
import rikardholm.insurance.domain.Insurance;
import rikardholm.insurance.domain.InsuranceNumber;
import rikardholm.insurance.domain.InsuranceRepository;

public class OracleInsuranceRepository implements InsuranceRepository {

	@Override
	public Optional<Insurance> findBy(InsuranceNumber insuranceNumber) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Insurance> findBy(Customer customer) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void create(Insurance instance) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(Insurance instance) {
		throw new UnsupportedOperationException();
	}
}
