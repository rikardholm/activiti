package rikardholm.insurance.common.test.database;

import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.RunScript;

import javax.sql.DataSource;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.SQLException;

public class InMemoryDatabaseManager {
    public static final String URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    public static final String USERNAME = "sa";
    public static final String PASSWORD = "";

    public static final DataSource dataSource = inMemoryDataSource();

    public void migrate() {
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.migrate();
    }

    public void shutdownDatabase() {
        runSql(dataSource, "SHUTDOWN");
    }

    public static DataSource dataSource() {
        return dataSource;
    }

    private void runSql(DataSource dataSource, String sql) {
        try (Connection connection = dataSource.getConnection()) {
            RunScript.execute(connection, new StringReader(sql));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static DataSource inMemoryDataSource() {
        JdbcDataSource jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setURL(URL);
        jdbcDataSource.setUser(USERNAME);
        jdbcDataSource.setPassword(PASSWORD);
        return jdbcDataSource;
    }
}
