package rikardholm.insurance.infrastructure.postgres;

import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import rikardholm.insurance.domain.insurance.AbstractInsuranceRepositoryTest;
import rikardholm.insurance.domain.customer.CustomerRepository;
import rikardholm.insurance.domain.insurance.InsuranceRepository;
import rikardholm.insurance.infrastructure.PostgresCategory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:test/spring/datasource-postgres.xml",
		"classpath*:META-INF/insurance/spring/mybatis-context-postgres.xml"})
@Category(PostgresCategory.class)
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