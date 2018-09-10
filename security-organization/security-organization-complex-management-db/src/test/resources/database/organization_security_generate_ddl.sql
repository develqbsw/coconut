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
  c_code          character varying(255) not null,
  c_name          character varying(255) not null,
  c_state         character varying(255) not null,
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
  fk_user  bigint not null
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
  add constraint fk_org_unit_organization foreign key (fk_organization) references org.t_organization (pk_id);

--alter table org.t_user
--  add constraint fk_user_organization foreign key (fk_organization) references org.t_organization (pk_id);


--drop all
drop schema if exists sec cascade;

--create schema
create schema sec;

--create tables and sequence
create table sec.t_auth_params (
  pk_id             bigint                 not null,
  c_password        character varying(255),
  c_password_digest character varying(255),
  c_password_type   character varying(255) not null,
  c_pin             character varying(255),
  c_valid_from      timestamp with time zone,
  c_valid_to        timestamp with time zone,
  fk_account        bigint                 not null
);

create sequence sec.s_auth_params
  start with 1
  increment by 1;

create table sec.t_blocked_login (
  pk_id                 bigint                 not null,
  c_login               character varying(255) not null,
  c_ip                  character varying(255),
  c_blocked_from        timestamp with time zone,
  c_blocked_to          timestamp with time zone,
  c_invalid_login_count integer                not null
);

create sequence sec.s_blocked_login
  start with 1
  increment by 1;

create table sec.t_group (
  pk_id      bigint                 not null,
  c_code     character varying(255) not null,
  c_type     character varying(255) not null,
  c_category character varying(255)
);

create sequence sec.s_group
  start with 1
  increment by 1;

create table sec.t_organization (
  pk_id   bigint                 not null,
  d_type  character varying(31)  not null,
  c_code  character varying(255) not null,
  c_name  character varying(255) not null,
  c_email character varying(255),
  c_state character varying(255) not null
);

create sequence sec.s_organization
  start with 1
  increment by 1;

create table sec.t_role (
  pk_id  bigint                 not null,
  c_code character varying(255) not null
);

create sequence sec.s_role
  start with 1
  increment by 1;

create table sec.t_unit (
  pk_id           bigint                 not null,
  d_type          character varying(31)  not null,
  c_name          character varying(255) not null,
  fk_organization bigint                 not null
);

create sequence sec.s_unit
  start with 1
  increment by 1;

create table sec.t_account (
  pk_id           bigint                 not null,
  d_type          character varying(31)  not null,
  c_uid           character varying(255)  not null,
  c_login         character varying(255) not null,
  c_email         character varying(255),
  c_state         character varying(255) not null,
  c_type          character varying(255) not null,
  fk_default_unit bigint,
  fk_organization bigint                 not null,
  fk_user         bigint                 not null
);

create sequence sec.s_account
  start with 1
  increment by 1;

create table sec.t_x_group_group (
  fk_group          bigint not null,
  fk_excluded_group bigint not null
);

create table sec.t_x_group_role (
  fk_role  bigint not null,
  fk_group bigint not null
);

create table sec.t_x_group_unit (
  fk_unit  bigint not null,
  fk_group bigint not null
);

create table sec.t_x_account_unit_group (
  pk_id      bigint not null,
  fk_account bigint not null,
  fk_group   bigint not null,
  fk_unit    bigint
);

create sequence sec.s_x_account_unit_group
  start with 1
  increment by 1;

--constraints--
--primary key
alter table sec.t_auth_params
  add constraint pk_auth_params primary key (pk_id);

alter table sec.t_blocked_login
  add constraint pk_blocked_login primary key (pk_id);

alter table sec.t_group
  add constraint pk_group primary key (pk_id);

alter table sec.t_organization
  add constraint pk_organization primary key (pk_id);

alter table sec.t_role
  add constraint pk_role primary key (pk_id);

alter table sec.t_unit
  add constraint pk_unit primary key (pk_id);

alter table sec.t_account
  add constraint pk_account primary key (pk_id);

alter table sec.t_x_group_group
  add constraint pk_x_group_group primary key (fk_group, fk_excluded_group);

alter table sec.t_x_group_role
  add constraint pk_x_group_role primary key (fk_role, fk_group);

alter table sec.t_x_group_unit
  add constraint pk_x_group_unit primary key (fk_unit, fk_group);

alter table sec.t_x_account_unit_group
  add constraint pk_x_account_unit_group primary key (pk_id);

--unique
alter table sec.t_blocked_login
  add constraint uc_blocked_login_login_ip unique (c_login, c_ip);

alter table sec.t_group
  add constraint uc_group_code unique (c_code);

alter table sec.t_role
  add constraint uc_role_code unique (c_code);

alter table sec.t_unit
  add constraint uc_unit_name unique (c_name);

alter table sec.t_organization
  add constraint uc_organization_code unique (c_code);

alter table sec.t_account
  add constraint uc_account_login unique (c_login);

--foreign key
alter table sec.t_x_group_role
  add constraint fk_group_role_role foreign key (fk_role) references sec.t_role (pk_id);

alter table sec.t_x_group_role
  add constraint fk_group_role_group foreign key (fk_group) references sec.t_group (pk_id);

alter table sec.t_x_group_unit
  add constraint fk_group_unit_unit foreign key (fk_unit) references sec.t_unit (pk_id);

alter table sec.t_x_group_group
  add constraint fk_group_group_group foreign key (fk_group) references sec.t_group (pk_id);

alter table sec.t_x_group_unit
  add constraint fk_group_unit_group foreign key (fk_group) references sec.t_group (pk_id);

alter table sec.t_x_group_group
  add constraint fk_group_group_excluded_group foreign key (fk_excluded_group) references sec.t_group (pk_id);

alter table sec.t_x_account_unit_group
  add constraint fk_account_unit_group_unit foreign key (fk_unit) references sec.t_unit (pk_id);

alter table sec.t_x_account_unit_group
  add constraint fk_account_unit_group_account foreign key (fk_account) references sec.t_account (pk_id);

alter table sec.t_x_account_unit_group
  add constraint fk_account_unit_group_group foreign key (fk_group) references sec.t_group (pk_id);

alter table sec.t_auth_params
  add constraint fk_auth_params_account foreign key (fk_account) references sec.t_account (pk_id);

alter table sec.t_unit
  add constraint fk_unit_organization foreign key (fk_organization) references sec.t_organization (pk_id);

alter table sec.t_account
  add constraint fk_account_default_unit foreign key (fk_default_unit) references sec.t_unit (pk_id);

alter table sec.t_account
  add constraint fk_account_organization foreign key (fk_organization) references sec.t_organization (pk_id);

alter table sec.t_account
  add constraint fk_account_user foreign key (fk_user) references org.t_user (pk_id);


--insert into sec.t_organization (pk_id, d_type, c_code, c_name, c_email, c_state)
--    values (1, 'organization', 'unit_test_organization', 'unit_test_organization', 'unit_test_organization@qbsw.sk', 'ACTIVE');
