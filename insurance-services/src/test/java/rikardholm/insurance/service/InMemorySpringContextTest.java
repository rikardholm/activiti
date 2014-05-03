package rikardholm.insurance.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import rikardholm.insurance.service.insurance.CustomerRepository;
import rikardholm.insurance.service.insurance.InsuranceRepository;
import rikardholm.insurance.service.spar.SparService;

import static org.hamcrest.MatcherAssert.assertThat;
import static rikardholm.insurance.common.test.ApplicationContextMatchers.hasExactlyOneBeanOfType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:META-INF/insurance/spring/in-memory-services.xml")
public class InMemorySpringContextTest {
    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void wires_an_InsuranceRepository() throws Exception {
        assertThat(applicationContext, hasExactlyOneBeanOfType(InsuranceRepository.class));
    }

    @Test
    public void wires_a_CustomerRepository() throws Exception {
        assertThat(applicationContext, hasExactlyOneBeanOfType(CustomerRepository.class));
    }

    @Test
    public void wires_a_SparService() throws Exception {
         assertThat(applicationContext, hasExactlyOneBeanOfType(SparService.class));
    }
}
