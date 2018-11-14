-- **** oauth token table ****
-- rename user
alter table sec.t_oauth_token
  rename column fk_user to fk_account;

-- rename unique token
alter table sec.t_oauth_token
  rename constraint uk_oauth_token_token to uc_security_token_token;

-- rename constraints
alter table sec.t_oauth_token
  rename constraint fk_oauth_token_user to fk_oauth_token_account;
