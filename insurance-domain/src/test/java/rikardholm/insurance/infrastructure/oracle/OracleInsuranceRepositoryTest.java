package rikardholm.insurance.infrastructure.oracle;

import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import rikardholm.insurance.domain.AbstractInsuranceRepositoryTest;
import rikardholm.insurance.domain.CustomerRepository;
import rikardholm.insurance.domain.InsuranceRepository;
import rikardholm.insurance.infrastructure.OracleCategory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:META-INF/insurance/spring/mybatis-context-oracle.xml","classpath*:test/spring/datasource-oracle.xml"})
@Category(OracleCategory.class)
public class OracleInsuranceRepositoryTest extends AbstractInsuranceRepositoryTest{
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
