CREATE TABLE inbox
(
  id BIGINT PRIMARY KEY,
  uuid UUID NOT NULL,
  received_at DATETIME NOT NULL,
  payload CLOB NOT NULL
);
CREATE SEQUENCE inbox_seq
START WITH 1
INCREMENT BY 1;