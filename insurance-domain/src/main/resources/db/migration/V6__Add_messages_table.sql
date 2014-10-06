CREATE TABLE messages
(
  id BIGINT PRIMARY KEY,
  uuid UUID,
  received_at DATETIME,
  type VARCHAR(255),
  payload CLOB
);
CREATE SEQUENCE messages_seq
START WITH 1
INCREMENT BY 1;