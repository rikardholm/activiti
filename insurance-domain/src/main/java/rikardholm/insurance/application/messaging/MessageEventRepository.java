package rikardholm.insurance.application.messaging;

import rikardholm.insurance.domain.common.Repository;

import java.util.List;

public interface MessageEventRepository extends Repository<MessageEvent>{

    List<MessageEvent> findBy(Message message);
}
