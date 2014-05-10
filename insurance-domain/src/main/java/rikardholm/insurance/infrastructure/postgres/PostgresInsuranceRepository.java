package rikardholm.insurance.infrastructure.postgres;

import com.google.common.base.Optional;
import rikardholm.insurance.domain.*;

import java.util.List;

public class PostgresInsuranceRepository implements InsuranceRepository {

    private InsuranceMapper insuranceMapper;
    private CustomerMapper customerMapper;

    public PostgresInsuranceRepository(InsuranceMapper insuranceMapper, CustomerMapper customerMapper) {
        this.insuranceMapper = insuranceMapper;
        this.customerMapper = customerMapper;
    }

    @Override
    public com.google.common.base.Optional<Insurance> findBy(InsuranceNumber insuranceNumber) {
        Insurance insurance = insuranceMapper.findByInsuranceNumber(insuranceNumber);

        if (insurance == null) {
            return Optional.absent();
        }

        return Optional.of(insurance);
    }

    @Override
    public List<Insurance> findBy(Customer customer) {
        List<Insurance> insurances = (List<Insurance>) (List<? extends Insurance>) insuranceMapper.findByCustomer(customer);
        return insurances;
    }

    @Override
    public void create(Insurance instance) {
        Integer customerId = customerMapper.selectId(instance.getCustomer());

        insuranceMapper.insert(instance, customerId);
    }

    @Override
    public void delete(Insurance instance) {
        insuranceMapper.delete(instance);
    }
}
