package rikardholm.insurance.infrastructure.oracle;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import rikardholm.insurance.domain.AbstractCustomerRepositoryTest;
import rikardholm.insurance.domain.CustomerRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:test/spring/datasource-oracle.xml"})
@Ignore
public class OracleCustomerRepositoryTest extends AbstractCustomerRepositoryTest {
	@Override
	protected CustomerRepository getInstance() {
		return null;
	}
}
