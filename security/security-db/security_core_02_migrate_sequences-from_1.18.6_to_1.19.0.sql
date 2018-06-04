-- **** organization ****
alter sequence sec.t_organization_pk_id_seq
rename to s_organization;

-- **** role ****
alter sequence sec.t_role_pk_id_seq
rename to s_role;

-- **** unit ****
alter sequence sec.t_unit_pk_id_seq
rename to s_unit;

-- **** group ****
alter sequence sec.t_group_pk_id_seq
rename to s_group;

-- **** auth params ****
alter sequence sec.t_auth_params_pk_id_seq
rename to s_auth_params;

-- **** account ****
alter sequence sec.t_user_pk_id_seq
rename to s_account;

-- **** account unit group ****
alter sequence sec.t_x_group_user_pk_id_seq
rename to s_x_account_unit_group;

-- **** blocked login sequence ****
alter sequence sec.t_blocked_login_pk_id_seq
rename to s_blocked_login;

-- **** drop sequences ****
drop sequence sec.t_address_pk_id_seq;
drop sequence sec.t_licence_pk_id_seq;
