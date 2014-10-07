package rikardholm.insurance.infrastructure.h2;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import rikardholm.insurance.application.messaging.Message;
import rikardholm.insurance.application.messaging.MessageEvent;
import rikardholm.insurance.application.messaging.MessageEventRepository;
import rikardholm.insurance.application.messaging.impl.MessageEventImpl;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class H2MessageEventRepository implements MessageEventRepository {

    public static final String SEQUENCE = "SELECT message_events_seq.nextval FROM DUAL";
    public static final String INSERT = "INSERT INTO message_events (id, uuid, event) VALUES (:id, :uuid, :event)";
    public static final String SELECT = "SELECT * FROM message_events WHERE UUID=:uuid";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public H2MessageEventRepository(DataSource dataSource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void append(MessageEvent messageEvent) {
        Long id = namedParameterJdbcTemplate.getJdbcOperations().queryForObject(SEQUENCE, Long.class);

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        paramMap.put("uuid", messageEvent.getUUID());
        paramMap.put("event", messageEvent.getEvent());
        namedParameterJdbcTemplate.update(INSERT, paramMap);
    }

    @Override
    public List<MessageEvent> findByUUID(UUID uuid) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("uuid", uuid);
        List<MessageEvent> result = namedParameterJdbcTemplate.query(SELECT, paramMap, new MessageEventRowMapper());
        return result;
    }

    @Deprecated
    @Override
    public List<MessageEvent> findBy(Message message) {
        return null;
    }

    @Deprecated
    @Override
    public void save(MessageEvent instance) {

    }

    @Deprecated
    @Override
    public void delete(MessageEvent instance) {

    }

    private class MessageEventRowMapper implements RowMapper<MessageEvent> {

        @Override
        public MessageEvent mapRow(ResultSet rs, int rowNum) throws SQLException {
            UUID uuid = UUID.fromString(rs.getString("uuid"));
            return new MessageEventImpl(uuid, rs.getString("event"));
        }
    }
}
