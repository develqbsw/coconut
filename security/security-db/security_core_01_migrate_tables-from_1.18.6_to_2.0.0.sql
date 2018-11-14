-- **** organization table ****
-- add state
alter table sec.t_organization
  add column c_state character varying(255);

update sec.t_organization
set c_state =
case
when c_flag_enabled = true
  then 'ACTIVE'
when c_flag_enabled is null
  then 'ACTIVE'
else 'INACTIVE'
end;

alter table sec.t_organization
  alter c_state set not null;

-- add not null to code and name
alter table sec.t_organization
  alter c_code set not null;

alter table sec.t_organization
  alter c_name set not null;

-- add unique to code
alter table sec.t_organization
  add constraint uc_organization_code unique (c_code);

-- drop columns
alter table sec.t_organization
  drop column c_change_date_time,
  drop column c_operation_id,
  drop column c_flag_enabled,
  drop column c_phone,
  drop column c_fax,
  drop column fk_address,
  drop column fk_changed_by;

-- **** role table ****
-- add not null to code
alter table sec.t_role
  alter c_code set not null;

-- rename unique code
alter table sec.t_role
  rename constraint uk_role_code to uc_role_code;

-- drop columns
alter table sec.t_role
  drop column c_change_date_time,
  drop column c_operation_id,
  drop column fk_changed_by;

-- **** unit table ****
-- rename unique name
alter table sec.t_unit
  rename constraint uk_unit_name to uc_unit_name;

-- drop columns
alter table sec.t_unit
  drop column c_change_date_time,
  drop column c_operation_id,
  drop column fk_address,
  drop column fk_changed_by;

-- **** group table ****
-- add type
alter table sec.t_group
  add column c_type character varying(255);

update sec.t_group
set c_type =
case
when c_flag_system = true
  then 'TECHNICAL'
when c_flag_system is null
  then 'STANDARD'
else 'STANDARD'
end;

alter table sec.t_group
  alter c_type set not null;

-- add not null to code
alter table sec.t_group
  alter c_code set not null;

-- rename unique code
alter table sec.t_group
  rename constraint uk_group_code to uc_group_code;

-- drop columns
alter table sec.t_group
  drop column c_change_date_time,
  drop column c_operation_id,
  drop column c_flag_system,
  drop column fk_changed_by;

-- **** auth params table ****
-- rename user
alter table sec.t_auth_params
  rename column fk_user to fk_account;

-- drop columns
alter table sec.t_auth_params
  drop column c_change_date_time,
  drop column c_operation_id,
  drop column fk_changed_by;

-- rename constraints
alter table sec.t_auth_params
  rename constraint fk_authparams_user to fk_authparams_account;

-- **** account table ****
-- add state
alter table sec.t_user
  add column c_state character varying(255);

update sec.t_user
set c_state =
case
when c_flag_enabled = true
  then 'ACTIVE'
when c_flag_enabled is null
  then 'ACTIVE'
else 'INACTIVE'
end;

alter table sec.t_user
  alter c_state set not null;

-- update type
update sec.t_user
set c_type = 'PERSONAL'
where c_type = 'PERSON';

-- add not null to login and type
alter table sec.t_user
  alter c_login set not null;

alter table sec.t_user
  alter c_type set not null;

-- rename unique
alter table sec.t_user
  rename constraint uk_user_login to uc_account_login;

-- drop columns
alter table sec.t_user
  drop column c_change_date_time,
  drop column c_operation_id,
  drop column c_flag_enabled,
  drop column c_name,
  drop column c_surname,
  drop column c_degree,
  drop column c_working_position,
  drop column fk_address,
  drop column fk_changed_by;

-- rename table and constraints
alter table sec.t_user
  rename to t_account;
alter table sec.t_account
  rename constraint fk_user_organization to fk_account_organization;
alter table sec.t_account
  rename constraint pk_user to pk_account;
alter table sec.t_account
  rename constraint fk_user_defaultunit to fk_account_defaultunit;

-- **** account unit group table ****
-- rename user
alter table sec.t_x_group_user
  rename column fk_user to fk_account;

-- drop columns
alter table sec.t_x_group_user
  drop column c_change_date_time,
  drop column c_operation_id,
  drop column fk_changed_by;

-- rename table and constraints
alter table sec.t_x_group_user
  rename to t_x_account_unit_group;
alter table sec.t_x_account_unit_group
  rename constraint pk_x_group_user to pk_x_group_account;
alter table sec.t_x_account_unit_group
  rename constraint fk_groupuser_user to fk_groupuser_account;

-- **** blocked login table ****
-- rename unique
alter table sec.t_blocked_login
  rename constraint uk_blocked_login to uc_blocked_login_login_ip;

-- drop columns
alter table sec.t_blocked_login
  drop column c_change_date_time,
  drop column c_operation_id,
  drop column fk_changed_by;

-- **** drop tables ****
drop table sec.t_address;
drop table sec.t_licence;

-- **** alter account with uid and add user ****
-- create user
create table sec.t_user
(
    pk_id bigint not null,
    d_type character varying(31) not null
);
alter table sec.t_user
    add constraint pk_user primary key (pk_id);

alter table sec.t_user owner to :adm_user;
grant all on table sec.t_user to :adm_user;
grant select, update, insert, delete on table sec.t_user to :gw_user;
grant select on table sec.t_user to :preview_user;

-- add user to account
alter table sec.t_account
  add column fk_user bigint;
alter table sec.t_account
  add constraint fk_account_user foreign key (fk_user) references sec.t_user(pk_id);

-- add uid to account
create extension if not exists "uuid-ossp";

alter table sec.t_account
  add column c_uid character varying(255);

update sec.t_account
  set c_uid = uuid_generate_v4();

alter table sec.t_account
  alter c_uid set not null;
