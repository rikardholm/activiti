package rikardholm.insurance.application.messaging;

import java.util.List;

public interface MessageRepository<M extends Message> {
    void add(M message);

    <T extends M> List<T> find(Class<T> type);
}
