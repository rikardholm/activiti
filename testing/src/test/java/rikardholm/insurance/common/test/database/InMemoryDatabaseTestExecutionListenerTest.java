package rikardholm.insurance.common.test.database;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import javax.sql.DataSource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {InMemoryDatabaseTestExecutionListenerTest.Context.class, InMemoryDatabaseTestExecutionListener.DataSourceContext.class})
@InMemoryDatabase
public class InMemoryDatabaseTestExecutionListenerTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private DataSource dataSource;

    @Test
    public void creates_a_database() throws Exception {
        DataSourceConsumer bean = applicationContext.getBean(DataSourceConsumer.class);
        assertNotNull(bean);
        assertNotNull(bean.dataSource);
    }

    @Test
    public void loads_tables() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        Integer count = jdbcTemplate.queryForObject("SELECT count(*) FROM test", Integer.class);

        assertNotNull(count);
    }

    @Test
    public void clears_db_after_each_test_1() throws Exception {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update("INSERT INTO test (id,name) VALUES (1,'first test')");

        Integer count = jdbcTemplate.queryForObject("SELECT count(*) FROM test", Integer.class);

        assertEquals(Integer.valueOf(1), count);
    }

    @Test
    public void clears_db_after_each_test_2() throws Exception {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update("INSERT INTO test (id,name) VALUES (2,'second test')");

        Integer count = jdbcTemplate.queryForObject("SELECT count(*) FROM test", Integer.class);

        assertEquals(Integer.valueOf(1), count);
    }

    public static final class Context {
        @Bean
        public DataSourceConsumer dataSourceConsumer(DataSource dataSource) {
            return new DataSourceConsumer(dataSource);
        }
    }

    private static final class DataSourceConsumer {
        final DataSource dataSource;

        private DataSourceConsumer(DataSource dataSource) {
            this.dataSource = dataSource;
        }
    }

}