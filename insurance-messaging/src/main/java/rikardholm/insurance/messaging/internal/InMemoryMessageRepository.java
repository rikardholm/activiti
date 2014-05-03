package rikardholm.insurance.messaging.internal;

import rikardholm.insurance.messaging.MessageRepository;
import rikardholm.insurance.messaging.message.Message;

import java.util.ArrayList;
import java.util.List;

public class InMemoryMessageRepository<M extends Message> implements MessageRepository<M> {
    private List<M> store = new ArrayList<M>();

    @Override
    public void add(M message) {
        store.add(message);
    }

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
}
