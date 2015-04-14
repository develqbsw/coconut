--drop all
DROP SCHEMA IF EXISTS cfg CASCADE;

--create schema
CREATE SCHEMA cfg;

--create tables and sequence
CREATE TABLE cfg.t_system_parameter (
    pk_id bigint NOT NULL,
    c_date_value timestamp without time zone,
    c_description character varying(255),
    c_float_value numeric(19,2),
    c_integer_value integer,
    c_module character varying(255),
    c_name character varying(255),
    c_string_value character varying(255),
    c_valid_from_date timestamp without time zone,
    c_valid_to_date timestamp without time zone,
    c_encrypted_value character varying(255)
);

CREATE SEQUENCE cfg.t_system_parameter_pk_id_seq START WITH 1 INCREMENT BY 1;

--constraints--
--primary key
ALTER TABLE cfg.t_system_parameter
    ADD CONSTRAINT pk_system_parameter PRIMARY KEY (pk_id);
    
--unique
ALTER TABLE cfg.t_system_parameter
    ADD CONSTRAINT uk_system_parameter_name UNIQUE (c_name);