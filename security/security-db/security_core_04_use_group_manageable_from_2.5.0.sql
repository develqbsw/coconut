-- **** group table - group manageable ****
-- add updated and updated_by

alter table sec.t_group
  add column c_updated timestamp with time zone,
  add column fk_updated_by bigint;

update sec.t_group
  set d_type = 'groupManageable';