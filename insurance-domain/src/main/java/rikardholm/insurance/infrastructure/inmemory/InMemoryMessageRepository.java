package rikardholm.insurance.infrastructure.inmemory;

import rikardholm.insurance.application.messaging.Message;
import rikardholm.insurance.application.messaging.MessageRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryMessageRepository<M extends Message> implements MessageRepository<M> {
    private List<M> store = new ArrayList<M>();

    @Override
    public <T extends M> List<T> find(Class<T> type) {
        List<T> foundMessages = new ArrayList<T>();
        for (M message : store) {
            if (messageMatchesType(message, type)) {
                foundMessages.add(type.cast(message));
            }
        }

        return foundMessages;
    }

    private boolean messageMatchesType(Object message, Class<?> type) {
        return type.isAssignableFrom(message.getClass());
    }

    @Override
    public void create(M instance) {
        store.add(instance);
    }

    @Override
    public void delete(M instance) {
        throw new UnsupportedOperationException();
    }
}
