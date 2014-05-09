package rikardholm.insurance.infrastructure.postgres;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import rikardholm.insurance.domain.AbstractCustomerRepositoryTest;
import rikardholm.insurance.domain.CustomerRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:test/spring/datasource-postgres.xml",
        "classpath*:META-INF/insurance/spring/postgres-mybatis-context.xml"})
public class PostgresCustomerRepositoryTest extends AbstractCustomerRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    protected CustomerRepository getInstance() {
        return customerRepository;
    }
}
