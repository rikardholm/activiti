package rikardholm.insurance.application.messaging;

import rikardholm.insurance.domain.common.Repository;

import java.util.List;

public interface MessageRepository3<M extends Message> extends Repository<M> {

    <T extends M> List<T> find(Class<T> type);
}
