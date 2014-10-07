package rikardholm.insurance.application.messaging;

import rikardholm.insurance.domain.common.Repository;

import java.util.List;
import java.util.UUID;

public interface MessageEventRepository extends Repository<MessageEvent> {
    void append(MessageEvent messageEvent);

    List<MessageEvent> findByUUID(UUID uuid);

    @Deprecated
    List<MessageEvent> findBy(Message message);
}
