CREATE TABLE insurances
(
  id bigserial NOT NULL,
  customer_id bigserial NOT NULL,
  CONSTRAINT insurances_pkey PRIMARY KEY (id),
  CONSTRAINT insurances_customer_id_fkey FOREIGN KEY (customer_id)
      REFERENCES customers (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)