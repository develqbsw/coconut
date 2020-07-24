alter table sec.t_account
  drop constraint uc_account_login;

alter table sec.t_account
  drop constraint uc_account_uid;

alter table sec.t_account
  add constraint uc_account_login unique (c_login, fk_organization);

alter table sec.t_account
  add constraint uc_account_uid unique (c_uid, fk_organization);