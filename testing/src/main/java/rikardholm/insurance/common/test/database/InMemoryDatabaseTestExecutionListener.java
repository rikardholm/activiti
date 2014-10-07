package rikardholm.insurance.common.test.database;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import javax.sql.DataSource;

public class InMemoryDatabaseTestExecutionListener extends AbstractTestExecutionListener {

    public static final String IN_MEMORY_DATASOURCE = "classpath:rikardholm/insurance/common/test/database/inmemory-datasource-context.xml";
    private InMemoryDatabaseManager inMemoryDatabaseManager = new InMemoryDatabaseManager();

    @Override
    public void beforeTestMethod(TestContext testContext) throws Exception {
        inMemoryDatabaseManager.migrate();
    }

    @Override
    public void afterTestMethod(TestContext testContext) throws Exception {
        inMemoryDatabaseManager.shutdownDatabase();
    }

    @Configuration
    public static class DataSourceContext {
        @Bean
        public DataSource dataSource() {
            return InMemoryDatabaseManager.dataSource;
        }
    }
}
