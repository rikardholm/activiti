package rikardholm.insurance.messaging;

import rikardholm.insurance.messaging.message.Message;

import java.util.List;

public interface MessageRepository<M extends Message> {
    void add(M message);

    <T extends M> List<T> find(Class<T> type);
}
