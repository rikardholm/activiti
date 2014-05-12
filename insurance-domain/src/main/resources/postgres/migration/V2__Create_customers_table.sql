CREATE TABLE customers
(
  id bigserial NOT NULL,
  personal_identifier character varying(11),
  CONSTRAINT customers_pkey PRIMARY KEY (id),
  CONSTRAINT customers_personal_identifier_key UNIQUE (personal_identifier)
);
COMMENT ON COLUMN customers.personal_identifier IS 'Personal Identification number YYMMDD-XXXX';