-- **** group table ****
-- add state
alter table sec.t_group
  add column c_state character varying(255);

update sec.t_group
set c_state = 'ACTIVE'
where c_state is null;

alter table sec.t_group
  alter c_state set not null;

-- **** group table ****
-- add type
alter table sec.t_group
  add column d_type character varying(20);

update sec.t_group
  set d_type = 'group'
where d_type is null;

alter table sec.t_group
  alter d_type set not null;