package rikardholm.insurance.infrastructure.postgres;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import rikardholm.insurance.domain.AbstractInsuranceRepositoryTest;
import rikardholm.insurance.domain.CustomerRepository;
import rikardholm.insurance.domain.InsuranceRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:test/spring/datasource-postgres.xml",
        "classpath*:META-INF/insurance/spring/postgres-mybatis-context.xml"})
public class PostgresInsuranceRepositoryTest extends AbstractInsuranceRepositoryTest{

    @Autowired
    private InsuranceRepository insuranceRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    protected InsuranceRepository getInstance() {
        return insuranceRepository;
    }

    @Override
    protected CustomerRepository getCustomerRepository() {
        return customerRepository;
    }
}