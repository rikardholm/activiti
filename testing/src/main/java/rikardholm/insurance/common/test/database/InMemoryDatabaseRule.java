package rikardholm.insurance.common.test.database;

import org.junit.rules.ExternalResource;

import javax.sql.DataSource;

public class InMemoryDatabaseRule extends ExternalResource {

    private final InMemoryDatabaseManager inMemoryDatabaseManager = new InMemoryDatabaseManager();

    public final DataSource dataSource = InMemoryDatabaseManager.dataSource;

    @Override
    protected void before() throws Throwable {
        inMemoryDatabaseManager.migrate();
    }

    @Override
    protected void after() {
        inMemoryDatabaseManager.shutdownDatabase();
    }
}
