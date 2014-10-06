CREATE TABLE customers
(
  id BIGINT PRIMARY KEY,
  personal_identifier VARCHAR(11) UNIQUE,
  address VARCHAR(255)
);