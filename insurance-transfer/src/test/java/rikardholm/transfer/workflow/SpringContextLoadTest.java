package rikardholm.transfer.workflow;

import org.activiti.engine.FormService;
import org.activiti.engine.RuntimeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import rikardholm.insurance.common.test.database.InMemoryDatabaseTestExecutionListener;
import rikardholm.insurance.transfer.ProcessDispatcher;
import rikardholm.insurance.transfer.task.SparDelegate;

import static org.hamcrest.MatcherAssert.assertThat;
import static rikardholm.insurance.common.test.hamcrest.ApplicationContextMatchers.hasExactlyOneBeanOfType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:META-INF/insurance/spring/insurance-transfer-context.xml",
        "classpath*:META-INF/insurance/spring/in-memory-application-context.xml",
        "classpath*:META-INF/insurance/spring/domain-context.xml",
        "classpath*:META-INF/insurance/spring/activiti.spring.cfg.xml",
        "classpath*:test/spring/activiti-datasource-inmemory.cfg.xml",
        InMemoryDatabaseTestExecutionListener.APPLICATION_CONTEXT_PATH})
public class SpringContextLoadTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void wires_a_ProcessDispatcher() throws Exception {
        assertThat(applicationContext, hasExactlyOneBeanOfType(ProcessDispatcher.class));
    }

    @Test
    public void wires_a_FormService() throws Exception {
        assertThat(applicationContext, hasExactlyOneBeanOfType(FormService.class));
    }

    @Test
    public void wires_a_RuntimeService() throws Exception {
        assertThat(applicationContext, hasExactlyOneBeanOfType(RuntimeService.class));
    }

    @Test
    public void wires_a_SparDelegate() throws Exception {
        assertThat(applicationContext, hasExactlyOneBeanOfType(SparDelegate.class));
    }
}
