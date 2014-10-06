package rikardholm.insurance.application.messaging;

import java.time.Instant;
import java.util.List;
import java.util.SortedSet;

public interface MessageRepository2 {
    void append(Message message);
    List<Message> receivedAfter(Instant instant);
}
