package rikardholm.insurance.infrastructure.h2;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import rikardholm.insurance.application.messaging.Message;
import rikardholm.insurance.application.messaging.MessageBuilder;
import rikardholm.insurance.application.messaging.MessageRepository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class H2MessageRepository implements MessageRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final String sequenceSql;
    private final String insertSql;
    private final String selectSql;

    public H2MessageRepository(DataSource dataSource, String tableName) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        sequenceSql = "SELECT " + tableName + "_seq.nextval FROM DUAL";
        insertSql = "INSERT INTO " + tableName + " (id, uuid, received_at, payload) VALUES (:id, :uuid, :receivedAt, :payload)";
        selectSql = "SELECT * FROM " + tableName + " WHERE received_at >= :receivedAt ORDER BY received_at";
    }

    @Override
    public void append(Message message) {
        Long id = namedParameterJdbcTemplate.getJdbcOperations().queryForObject(sequenceSql, Long.class);

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        paramMap.put("uuid", message.getUuid());
        paramMap.put("receivedAt", message.getReceivedAt().toString());
        paramMap.put("payload", message.getPayload());
        namedParameterJdbcTemplate.update(insertSql, paramMap);
    }

    @Override
    public List<Message> receivedAfter(Instant instant) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("receivedAt", instant.toString());
        List<Message> result = namedParameterJdbcTemplate.query(selectSql, paramMap, new MessageRowMapper());
        return result;
    }

    private static class MessageRowMapper implements RowMapper<Message> {

        @Override
        public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
            return MessageBuilder.aMessage()
                    .withUUID(UUID.fromString(rs.getString("uuid")))
                    .receivedAt(rs.getTimestamp("received_at").toInstant())
                    .payload(rs.getString("payload"))
                    .build();
        }
    }
}
