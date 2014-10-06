package rikardholm.insurance.infrastructure.h2;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import rikardholm.insurance.application.messaging.Message;
import rikardholm.insurance.application.messaging.MessageBuilder;
import rikardholm.insurance.application.messaging.MessageRepository2;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class H2MessageRepository implements MessageRepository2 {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public H2MessageRepository(DataSource dataSource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void append(Message message) {
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(message);

        Long id = namedParameterJdbcTemplate.getJdbcOperations().queryForObject("SELECT messages_seq.nextval FROM DUAL", Long.class);

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        paramMap.put("uuid", message.getUuid());
        paramMap.put("receivedAt", message.getReceivedAt().toString());
        paramMap.put("type", message.getType());
        paramMap.put("payload", message.getPayload());
        namedParameterJdbcTemplate.update("INSERT INTO messages (id, uuid, received_at, type, payload) VALUES (:id, :uuid, :receivedAt, :type, :payload)", paramMap);
    }

    @Override
    public List<Message> receivedAfter(Instant instant) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("receivedAt", instant.toString());
        List<Message> result = namedParameterJdbcTemplate.query("SELECT * FROM messages WHERE received_at >= :receivedAt ORDER BY received_at", paramMap, new MessageRowMapper());
        return result;
    }

    private static class MessageRowMapper implements RowMapper<Message> {

        @Override
        public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
            return MessageBuilder.aMessage()
                    .withUUID(UUID.fromString(rs.getString("uuid")))
                    .receivedAt(Instant.now())
                    .payload("blabla")
                    .type("fake")
                    .build();
        }
    }
}
