--drop all
DROP SCHEMA IF EXISTS sec CASCADE;

--create schema
CREATE SCHEMA sec;

--create tables and sequence
CREATE TABLE sec.t_address (
    pk_id bigint NOT NULL,
    c_change_date_time timestamp without time zone NOT NULL,
    c_city character varying(255) NOT NULL,
    c_house_number character varying(255),
    c_state character varying(255) NOT NULL,
    c_street character varying(255),
    c_zip_code character varying(255) NOT NULL,
    fk_changed_by bigint,
    c_operation_id bigint
);

CREATE SEQUENCE sec.t_address_pk_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE sec.t_auth_params (
    pk_id bigint NOT NULL,
    c_change_date_time timestamp without time zone NOT NULL,
    password character varying(255),
    passworddigest character varying(255),
    pin character varying(255),
    fk_user bigint NOT NULL,
    fk_changed_by bigint,
    c_operation_id bigint,
    c_password_type character varying(255) NOT NULL,
    c_valid_from timestamp without time zone,
    c_valid_to timestamp without time zone
);

CREATE SEQUENCE sec.t_auth_params_pk_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE sec.t_blocked_login (
    pk_id bigint NOT NULL,
    c_change_date_time timestamp without time zone NOT NULL,
    fk_changed_by bigint,
    c_operation_id bigint,
    c_login character varying(255) NOT NULL,
    c_ip character varying(255),
    c_blocked_from timestamp without time zone,
    c_blocked_to timestamp without time zone,
    c_invalid_login_count integer NOT NULL
);

CREATE SEQUENCE sec.t_blocked_login_pk_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE sec.t_group (
    pk_id bigint NOT NULL,
    c_change_date_time timestamp without time zone NOT NULL,
    category character varying(255),
    code character varying(255),
    flag_system boolean,
    fk_changed_by bigint,
    c_operation_id bigint
);

CREATE SEQUENCE sec.t_group_pk_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE sec.t_licence (
    type character varying(31) NOT NULL,
    pk_id bigint NOT NULL,
    c_change_date_time timestamp without time zone NOT NULL,
    flag_payed boolean,
    c_key character varying(255),
    price numeric(19,2),
    tax_id character varying(255),
    valid_from timestamp without time zone,
    valid_to timestamp without time zone,
    fk_organization bigint,
    fk_changed_by bigint,
    c_operation_id bigint
);

CREATE SEQUENCE sec.t_licence_pk_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE sec.t_organization (
    type character varying(31) NOT NULL,
    pk_id bigint NOT NULL,
    c_change_date_time timestamp without time zone NOT NULL,
    code character varying(255),
    email character varying(255),
    flag_enabled boolean,
    name character varying(255),
    phone character varying(255),
    fk_address bigint,
    fax character varying(255),
    fk_changed_by bigint,
    c_operation_id bigint
);

CREATE SEQUENCE sec.t_organization_pk_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE sec.t_role (
    pk_id bigint NOT NULL,
    c_change_date_time timestamp without time zone NOT NULL,
    code character varying(255),
    fk_changed_by bigint,
    c_operation_id bigint
);

CREATE SEQUENCE sec.t_role_pk_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE sec.t_unit (
    pk_id bigint NOT NULL,
    c_change_date_time timestamp without time zone NOT NULL,
    name character varying(255) NOT NULL,
    fk_address bigint,
    fk_organization bigint NOT NULL,
    fk_changed_by bigint,
    c_operation_id bigint,
    dtype character varying(31) NOT NULL
);

CREATE SEQUENCE sec.t_unit_pk_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE sec.t_user (
    pk_id bigint NOT NULL,
    c_change_date_time timestamp without time zone NOT NULL,
    email character varying(255),
    flag_enabled boolean,
    login character varying(255),
    name character varying(255),
    surname character varying(255),
    type character varying(255),
    fk_address bigint,
    fk_default_unit bigint,
    fk_organization bigint NOT NULL,
    degree character varying(255),
    working_position character varying(255),
    fk_changed_by bigint,
    c_operation_id bigint
);

CREATE SEQUENCE sec.t_user_pk_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE sec.t_x_group_group (
    fk_group bigint NOT NULL,
    fk_excluded_group bigint NOT NULL
);

CREATE TABLE sec.t_x_group_role (
    fk_role bigint NOT NULL,
    fk_group bigint NOT NULL
);

CREATE TABLE sec.t_x_group_unit (
    fk_unit bigint NOT NULL,
    fk_group bigint NOT NULL
);

CREATE TABLE sec.t_x_group_user (
    pk_id bigint NOT NULL,
    c_change_date_time timestamp without time zone NOT NULL,
    fk_group bigint NOT NULL,
    fk_unit bigint,
    fk_user bigint NOT NULL,
    fk_changed_by bigint,
    c_operation_id bigint
);

CREATE SEQUENCE sec.t_x_group_user_pk_id_seq START WITH 1 INCREMENT BY 1;

--constraints--
--primary key
ALTER TABLE sec.t_address
    ADD CONSTRAINT pk_address PRIMARY KEY (pk_id);

ALTER TABLE sec.t_auth_params
    ADD CONSTRAINT pk_auth_params PRIMARY KEY (pk_id);

ALTER TABLE sec.t_blocked_login
    ADD CONSTRAINT pk_blocked_login PRIMARY KEY (pk_id);
    
ALTER TABLE sec.t_group
    ADD CONSTRAINT pk_group PRIMARY KEY (pk_id);

ALTER TABLE sec.t_licence
    ADD CONSTRAINT pk_licence PRIMARY KEY (pk_id);

ALTER TABLE sec.t_organization
    ADD CONSTRAINT pk_organization PRIMARY KEY (pk_id);

ALTER TABLE sec.t_role
    ADD CONSTRAINT pk_role PRIMARY KEY (pk_id);

ALTER TABLE sec.t_unit
    ADD CONSTRAINT pk_unit PRIMARY KEY (pk_id);

ALTER TABLE sec.t_user
    ADD CONSTRAINT pk_user PRIMARY KEY (pk_id);

ALTER TABLE sec.t_x_group_group
    ADD CONSTRAINT pk_x_group_group PRIMARY KEY (fk_group, fk_excluded_group);

ALTER TABLE sec.t_x_group_role
    ADD CONSTRAINT pk_x_group_role PRIMARY KEY (fk_role, fk_group);

ALTER TABLE sec.t_x_group_unit
    ADD CONSTRAINT pk_x_group_unit PRIMARY KEY (fk_unit, fk_group);

ALTER TABLE sec.t_x_group_user
    ADD CONSTRAINT pk_x_group_user PRIMARY KEY (pk_id);
    
--unique
ALTER TABLE sec.t_blocked_login
    ADD CONSTRAINT uk_blocked_login UNIQUE (c_login, c_ip);
    
ALTER TABLE sec.t_group
    ADD CONSTRAINT uk_group_code UNIQUE (code);

ALTER TABLE sec.t_role
    ADD CONSTRAINT uk_role_code UNIQUE (code);

ALTER TABLE sec.t_unit
    ADD CONSTRAINT uk_unit_name UNIQUE (name);

ALTER TABLE sec.t_user
    ADD CONSTRAINT uk_user_login UNIQUE (login);

--foreign key
ALTER TABLE sec.t_organization
    ADD CONSTRAINT fk_organization_address FOREIGN KEY (fk_address) REFERENCES sec.t_address(pk_id);

ALTER TABLE sec.t_x_group_role
    ADD CONSTRAINT fk_group_role_role FOREIGN KEY (fk_role) REFERENCES sec.t_role(pk_id);

ALTER TABLE sec.t_x_group_role
    ADD CONSTRAINT fk_group_role_group FOREIGN KEY (fk_group) REFERENCES sec.t_group(pk_id);

ALTER TABLE sec.t_x_group_unit
    ADD CONSTRAINT fk_group_unit_unit FOREIGN KEY (fk_unit) REFERENCES sec.t_unit(pk_id);

ALTER TABLE sec.t_x_group_group
    ADD CONSTRAINT fk_group_group_group FOREIGN KEY (fk_group) REFERENCES sec.t_group(pk_id);

ALTER TABLE sec.t_x_group_unit
    ADD CONSTRAINT fk_group_unit_group FOREIGN KEY (fk_group) REFERENCES sec.t_group(pk_id);

ALTER TABLE sec.t_x_group_group
    ADD CONSTRAINT fk_group_group_excluded_group FOREIGN KEY (fk_excluded_group) REFERENCES sec.t_group(pk_id);

ALTER TABLE sec.t_x_group_user
    ADD CONSTRAINT fk_group_user_unit FOREIGN KEY (fk_unit) REFERENCES sec.t_unit(pk_id);

ALTER TABLE sec.t_x_group_user
    ADD CONSTRAINT fk_group_user_user FOREIGN KEY (fk_user) REFERENCES sec.t_user(pk_id);

ALTER TABLE sec.t_x_group_user
    ADD CONSTRAINT fk_group_user_group FOREIGN KEY (fk_group) REFERENCES sec.t_group(pk_id);

ALTER TABLE sec.t_address
    ADD CONSTRAINT fk_address_changed_by FOREIGN KEY (fk_changed_by) REFERENCES sec.t_user(pk_id);

ALTER TABLE sec.t_role
    ADD CONSTRAINT fk_role_changed_by FOREIGN KEY (fk_changed_by) REFERENCES sec.t_user(pk_id);

ALTER TABLE sec.t_licence
    ADD CONSTRAINT fk_licence_changed_by FOREIGN KEY (fk_changed_by) REFERENCES sec.t_user(pk_id);

ALTER TABLE sec.t_unit
    ADD CONSTRAINT fk_unit_changed_by FOREIGN KEY (fk_changed_by) REFERENCES sec.t_user(pk_id);

ALTER TABLE sec.t_group
    ADD CONSTRAINT fk_group_changed_by FOREIGN KEY (fk_changed_by) REFERENCES sec.t_user(pk_id);

ALTER TABLE sec.t_x_group_user
    ADD CONSTRAINT fk_x_group_user_changed_by FOREIGN KEY (fk_changed_by) REFERENCES sec.t_user(pk_id);

ALTER TABLE sec.t_organization
    ADD CONSTRAINT fk_organization_changed_by FOREIGN KEY (fk_changed_by) REFERENCES sec.t_user(pk_id);

ALTER TABLE sec.t_user
    ADD CONSTRAINT fk_user_changed_by FOREIGN KEY (fk_changed_by) REFERENCES sec.t_user(pk_id);

ALTER TABLE sec.t_auth_params
    ADD CONSTRAINT fk_auth_params_changed_by FOREIGN KEY (fk_changed_by) REFERENCES sec.t_user(pk_id);

ALTER TABLE sec.t_licence
    ADD CONSTRAINT fk_licence_organization FOREIGN KEY (fk_organization) REFERENCES sec.t_organization(pk_id);

ALTER TABLE sec.t_auth_params
    ADD CONSTRAINT fk_auth_params_user FOREIGN KEY (fk_user) REFERENCES sec.t_user(pk_id);

ALTER TABLE sec.t_unit
    ADD CONSTRAINT fk_unit_address FOREIGN KEY (fk_address) REFERENCES sec.t_address(pk_id);

ALTER TABLE sec.t_unit
    ADD CONSTRAINT fk_unit_organization FOREIGN KEY (fk_organization) REFERENCES sec.t_organization(pk_id);

ALTER TABLE sec.t_user
    ADD CONSTRAINT fk_user_default_unit FOREIGN KEY (fk_default_unit) REFERENCES sec.t_unit(pk_id);

ALTER TABLE sec.t_user
    ADD CONSTRAINT fk_user_address FOREIGN KEY (fk_address) REFERENCES sec.t_address(pk_id);

ALTER TABLE sec.t_user
    ADD CONSTRAINT fk_user_organization FOREIGN KEY (fk_organization) REFERENCES sec.t_organization(pk_id);