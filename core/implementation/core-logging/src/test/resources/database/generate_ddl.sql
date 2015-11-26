--drop all
DROP SCHEMA IF EXISTS log CASCADE;

--create schema
CREATE SCHEMA log;

--create type text for postgresql compatibility
CREATE TYPE TEXT AS VARCHAR(1000000);

--create tables and sequence
CREATE TABLE log.t_audit_log (
    pk_id bigint NOT NULL,
    c_operation_code character varying(255) NOT NULL,
    c_user_identifier character varying(255),
    c_request_date_time timestamp without time zone NOT NULL,
    c_result_description character varying(255),
    c_operation_result character varying(255),
    c_request_data text NOT NULL,
    c_result_uuid character varying(1024),
    c_uuid character varying(1024)
);

CREATE SEQUENCE log.t_audit_log_pk_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE log.t_installation_log (
    pk_id bigint NOT NULL,
    c_description character varying(255),
    c_name character varying(255),
    c_version character varying(255) NOT NULL
);

CREATE SEQUENCE log.t_installation_log_pk_id_seq START WITH 1 INCREMENT BY 1;

--constraints--
--primary key
ALTER TABLE log.t_audit_log
    ADD CONSTRAINT pk_audit_log PRIMARY KEY (pk_id);

ALTER TABLE log.t_installation_log
    ADD CONSTRAINT pk_installation_log PRIMARY KEY (pk_id);