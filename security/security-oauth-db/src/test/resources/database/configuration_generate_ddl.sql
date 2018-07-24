--drop all
drop schema if exists cfg cascade;

--create schema
create schema cfg;

--create tables and sequence
create table cfg.t_system_parameter (
  pk_id             bigint not null,
  c_date_value      timestamp with time zone,
  c_description     character varying(255),
  c_float_value     numeric(19, 2),
  c_integer_value   integer,
  c_module          character varying(255),
  c_name            character varying(255),
  c_string_value    character varying(255),
  c_valid_from_date timestamp with time zone,
  c_valid_to_date   timestamp with time zone,
  c_encrypted_value character varying(255)
);

create sequence cfg.t_system_parameter_pk_id_seq
  start with 1
  increment by 1;

--constraints--
--primary key
alter table cfg.t_system_parameter
  add constraint pk_system_parameter primary key (pk_id);

--unique
alter table cfg.t_system_parameter
  add constraint uk_system_parameter_name unique (c_name);
