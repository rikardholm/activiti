package rikardholm.insurance.infrastructure.inmemory;

import rikardholm.insurance.application.messaging.Message;
import rikardholm.insurance.application.messaging.MessageEvent;
import rikardholm.insurance.application.messaging.MessageEventRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InMemoryMessageEventRepository implements MessageEventRepository {
    private List<MessageEvent> events = new ArrayList<>();

    @Override
    public void save(MessageEvent instance) {
          events.add(instance);
    }

    @Override
    public void delete(MessageEvent instance) {
           events.remove(instance);
    }

    @Override
    public void append(MessageEvent messageEvent) {

    }

    @Override
    public List<MessageEvent> findByUUID(UUID uuid) {
        return null;
    }

    @Override
    public List<MessageEvent> findBy(Message message) {
        return events;
    }
}
