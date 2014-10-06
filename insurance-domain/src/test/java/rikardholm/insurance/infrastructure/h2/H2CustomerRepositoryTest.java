package rikardholm.insurance.infrastructure.h2;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import rikardholm.insurance.common.test.database.InMemoryDatabase;
import rikardholm.insurance.common.test.database.InMemoryDatabaseTestExecutionListener;
import rikardholm.insurance.domain.customer.AbstractCustomerRepositoryTest;
import rikardholm.insurance.domain.customer.CustomerRepository;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:META-INF/insurance/spring/repository-context.xml",
        InMemoryDatabaseTestExecutionListener.APPLICATION_CONTEXT_PATH})
@InMemoryDatabase
public class H2CustomerRepositoryTest extends AbstractCustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    protected CustomerRepository getInstance() {
        return customerRepository;
    }
}
