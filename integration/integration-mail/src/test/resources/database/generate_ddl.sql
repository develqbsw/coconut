--drop all
DROP SCHEMA IF EXISTS apsys CASCADE;

--create schema
CREATE SCHEMA apsys;

--create type oid for postgresql compatibility
CREATE TYPE OID AS BLOB;

--create tables and sequence
CREATE TABLE apsys.t_attachment (
    pk_id bigint NOT NULL,
    c_content_type character varying(255),
    c_data oid NOT NULL,
    c_file_name character varying(255) NOT NULL,
    fk_mail bigint NOT NULL
);

CREATE SEQUENCE apsys.t_attachment_pk_id_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE apsys.t_mail (
	pk_id bigint NOT NULL,
    d_type character varying(31) NOT NULL,
    c_attempt_counter integer NOT NULL,
    c_bcc character varying(255),
    c_body character varying(1024),
    c_cc character varying(255),
    c_created timestamp with time zone,
    c_from character varying(255) NOT NULL,
    c_sent timestamp with time zone,
    c_state character varying(255) NOT NULL,
    c_subject character varying(255),
    c_to character varying(255)
);

CREATE SEQUENCE apsys.t_mail_pk_id_seq START WITH 1 INCREMENT BY 50;

--constraints--
--primary key
ALTER TABLE apsys.t_attachment
    ADD CONSTRAINT pk_attachment PRIMARY KEY (pk_id);
    
ALTER TABLE apsys.t_mail
    ADD CONSTRAINT pk_mail PRIMARY KEY (pk_id);

--foreign
ALTER TABLE apsys.t_attachment
    ADD CONSTRAINT fk_attachment_mail FOREIGN KEY (fk_mail) REFERENCES apsys.t_mail(pk_id);
