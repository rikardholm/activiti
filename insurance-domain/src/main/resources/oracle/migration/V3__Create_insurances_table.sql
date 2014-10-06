CREATE TABLE insurances
(
  id NUMBER PRIMARY KEY,
  customer_id REFERENCES customers (id) NOT NULL
);