CREATE SEQUENCE public.product_sequence
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE TABLE public.product
(
    product_id integer NOT NULL DEFAULT nextval('product_sequence'),
    name character varying(100) ,
    serial_no character varying(30) ,
    catalog character varying(50)  DEFAULT 'Default',
    description character varying(512) ,
    product_status character varying(50)  DEFAULT 'Inactive',
    created_by character varying(100) ,
    created_time timestamp(6) with time zone,
    updated_by character varying(100) ,
    updated_time timestamp(6) with time zone,
    CONSTRAINT product_pkey PRIMARY KEY (product_id),
    CONSTRAINT product_name_key UNIQUE (name),
    CONSTRAINT product_serial_no_key UNIQUE (serial_no)

);