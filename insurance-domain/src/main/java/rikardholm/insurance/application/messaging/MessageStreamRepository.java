package rikardholm.insurance.application.messaging;

public interface MessageStreamRepository {
    MessageStream load(String name);
}
