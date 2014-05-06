package rikardholm.insurance.application.messaging;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static rikardholm.insurance.common.test.ApplicationContextMatchers.hasExactlyOneBeanOfType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:META-INF/insurance/spring/in-memory-messaging.xml")
public class InMemorySpringContextTest {
    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void wires_an_InboxRepository() throws Exception {
        assertThat(applicationContext, hasExactlyOneBeanOfType(InboxRepository.class));
    }

    @Test
    public void wires_an_OutboxRepository() throws Exception {
        assertThat(applicationContext, hasExactlyOneBeanOfType(OutboxRepository.class));
    }
}
