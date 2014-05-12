package rikardholm.insurance.infrastructure.oracle;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import rikardholm.insurance.domain.AbstractCustomerRepositoryTest;
import rikardholm.insurance.domain.CustomerRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:META-INF/insurance/spring/mybatis-context-oracle.xml"
		, "classpath*:test/spring/datasource-oracle.xml"})
public class OracleCustomerRepositoryTest extends AbstractCustomerRepositoryTest {
	@Autowired
	private CustomerRepository customerRepository;

	@Override
	protected CustomerRepository getInstance() {
		return customerRepository;
	}
}
