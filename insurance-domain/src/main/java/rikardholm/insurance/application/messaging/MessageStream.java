package rikardholm.insurance.application.messaging;

import java.time.Instant;
import java.util.List;

public interface MessageStream {
    void append(Message message);
    List<Message> after(Instant instant);
}
