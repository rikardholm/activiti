package rikardholm.insurance.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import rikardholm.insurance.application.spar.SparService;

import static org.hamcrest.MatcherAssert.assertThat;
import static rikardholm.insurance.common.test.hamcrest.ApplicationContextMatchers.hasExactlyOneBeanOfType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:META-INF/insurance/spring/in-memory-application-context.xml")
public class InMemoryApplicationContextTest {
    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void wires_a_SparService() throws Exception {
        assertThat(applicationContext, hasExactlyOneBeanOfType(SparService.class));
    }
}
