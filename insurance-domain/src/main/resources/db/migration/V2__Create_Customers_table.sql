CREATE TABLE customers
(
  id BIGINT PRIMARY KEY,
  personal_identifier VARCHAR(11) UNIQUE,
  address VARCHAR(255)
);
CREATE SEQUENCE customers_seq
START WITH 1
INCREMENT BY 1;