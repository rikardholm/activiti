package rikardholm.transfer.workflow;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import rikardholm.insurance.transfer.MessageHandler;
import rikardholm.insurance.transfer.ProcessDispatcher;

import static org.hamcrest.MatcherAssert.assertThat;
import static rikardholm.insurance.common.test.ApplicationContextMatchers.hasExactlyOneBeanOfType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:META-INF/insurance/spring/insurance-transfer-context.xml",
        "classpath*:META-INF/insurance/spring/in-memory-messaging.xml",
        "classpath*:META-INF/insurance/spring/in-memory-services.xml",
        "classpath*:META-INF/insurance/spring/activiti-inmemory-context.xml"})
public class SpringContextLoadTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void wires_a_MessageHandler() throws Exception {
        assertThat(applicationContext, hasExactlyOneBeanOfType(MessageHandler.class));
    }

    @Test
    public void wires_a_ProcessDispatcher() throws Exception {
        assertThat(applicationContext, hasExactlyOneBeanOfType(ProcessDispatcher.class));

    }
}
