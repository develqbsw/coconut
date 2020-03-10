-- **** group table ****
-- add type, updated and updated_by

alter table sec.t_group
  add column c_updated timestamp with time zone;

alter table sec.t_group
  add column fk_updated_by bigint;

update sec.t_group
  set d_type = 'groupManageable';