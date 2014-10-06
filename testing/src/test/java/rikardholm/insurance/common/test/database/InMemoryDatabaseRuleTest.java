package rikardholm.insurance.common.test.database;

import org.junit.Rule;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class InMemoryDatabaseRuleTest {
    @Rule
    public InMemoryDatabaseRule database = new InMemoryDatabaseRule();

    @Test
    public void creates_a_database_and_table() throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(database.dataSource);
        Integer count = jdbcTemplate.queryForObject("SELECT count(*) FROM test", Integer.class);

        assertNotNull(count);
    }

    @Test
    public void clears_db_after_each_test_1() throws Exception {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(database.dataSource);

        jdbcTemplate.update("INSERT INTO test (id,name) VALUES (1,'first test')");

        Integer count = jdbcTemplate.queryForObject("SELECT count(*) FROM test", Integer.class);

        assertEquals(Integer.valueOf(1), count);
    }

    @Test
    public void clears_db_after_each_test_2() throws Exception {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(database.dataSource);

        jdbcTemplate.update("INSERT INTO test (id,name) VALUES (2,'second test')");

        Integer count = jdbcTemplate.queryForObject("SELECT count(*) FROM test", Integer.class);

        assertEquals(Integer.valueOf(1), count);
    }
}