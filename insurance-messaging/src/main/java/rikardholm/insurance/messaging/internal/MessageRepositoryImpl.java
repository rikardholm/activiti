package rikardholm.insurance.messaging.internal;

import rikardholm.insurance.messaging.MessageRepository;
import rikardholm.insurance.messaging.message.Message;

import java.util.List;

public class MessageRepositoryImpl<M extends Message> implements MessageRepository<M> {
    @Override
    public void add(M message) {

    }

    @Override
    public <T extends M> List<T> find(Class<T> type) {
        return null;
    }
}
