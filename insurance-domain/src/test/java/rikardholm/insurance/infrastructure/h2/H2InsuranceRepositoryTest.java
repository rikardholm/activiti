package rikardholm.insurance.infrastructure.h2;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import rikardholm.insurance.common.test.database.InMemoryDatabase;
import rikardholm.insurance.common.test.database.InMemoryDatabaseTestExecutionListener;
import rikardholm.insurance.domain.customer.CustomerRepository;
import rikardholm.insurance.domain.insurance.AbstractInsuranceRepositoryTest;
import rikardholm.insurance.domain.insurance.InsuranceRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:META-INF/insurance/spring/domain-context.xml",
        InMemoryDatabaseTestExecutionListener.APPLICATION_CONTEXT_PATH})
@InMemoryDatabase
public class H2InsuranceRepositoryTest extends AbstractInsuranceRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private InsuranceRepository insuranceRepository;

    @Override
    protected CustomerRepository getCustomerRepository() {
        return customerRepository;
    }

    @Override
    protected InsuranceRepository getInstance() {
        return insuranceRepository;
    }
}
