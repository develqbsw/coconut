--drop all
drop schema if exists org cascade;

--create schema
create schema org;

--create tables and sequence
create table org.t_organization (
  pk_id   bigint                 not null,
  d_type  character varying(31)  not null,
  c_code  character varying(255) not null,
  c_name  character varying(255) not null,
  c_email character varying(255),
  c_state character varying(255) not null
);

create sequence org.s_organization
  start with 1
  increment by 1;

create table org.t_unit (
  pk_id           bigint                 not null,
  d_type          character varying(31)  not null,
  c_code  character varying(255) not null,
  c_name  character varying(255) not null,
  c_state character varying(255) not null,
  fk_organization bigint                 not null
);

create sequence org.s_unit
  start with 1
  increment by 1;

create table org.t_user (
  pk_id   bigint                 not null,
  d_type  character varying(31)  not null,
  c_state character varying(255) not null
  --fk_organization bigint                 not null
);

create sequence org.s_user
  start with 1
  increment by 1;

create table org.t_x_unit_user (
  fk_unit  bigint not null,
  fk_user bigint not null
);

--constraints--
--primary key
alter table org.t_organization
  add constraint pk_organization primary key (pk_id);

alter table org.t_unit
  add constraint pk_unit primary key (pk_id);

alter table org.t_user
  add constraint pk_user primary key (pk_id);

alter table org.t_x_unit_user
  add constraint pk_x_unit_user primary key (fk_unit, fk_user);

--unique
alter table org.t_organization
  add constraint uc_organization_code unique (c_code);

alter table org.t_unit
  add constraint uc_unit_code unique (c_code);

--foreign key
alter table org.t_x_unit_user
  add constraint fk_unit_user_unit foreign key (fk_unit) references org.t_unit (pk_id);

alter table org.t_x_unit_user
  add constraint fk_unit_user_user foreign key (fk_user) references org.t_user (pk_id);

alter table org.t_unit
  add constraint fk_unit_organization foreign key (fk_organization) references org.t_organization (pk_id);

--alter table org.t_user
--  add constraint fk_user_organization foreign key (fk_organization) references org.t_organization (pk_id);
