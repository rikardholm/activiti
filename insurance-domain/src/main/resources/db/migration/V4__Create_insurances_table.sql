CREATE TABLE insurances
(
  id NUMBER PRIMARY KEY,
  customer_id BIGINT NOT NULL,
  insurance_number NUMBER UNIQUE NOT NULL,
  FOREIGN KEY (customer_id)
  REFERENCES customers(id)
);