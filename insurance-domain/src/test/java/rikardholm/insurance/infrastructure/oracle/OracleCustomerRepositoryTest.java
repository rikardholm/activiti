package rikardholm.insurance.infrastructure.oracle;

import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import rikardholm.insurance.domain.customer.AbstractCustomerRepositoryTest;
import rikardholm.insurance.domain.customer.CustomerRepository;
import rikardholm.insurance.infrastructure.OracleCategory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:META-INF/insurance/spring/mybatis-context-oracle.xml",
        "classpath*:test/spring/datasource-oracle.xml"})
@Category(OracleCategory.class)
public class OracleCustomerRepositoryTest extends AbstractCustomerRepositoryTest {
	@Autowired
	private CustomerRepository customerRepository;

	@Override
	protected CustomerRepository getInstance() {
		return customerRepository;
	}
}
