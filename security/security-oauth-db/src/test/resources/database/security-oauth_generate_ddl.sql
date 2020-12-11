--create tables and sequence
create table sec.t_oauth_token
(
  pk_id              bigint                   not null,
  d_type             character varying(31)    not null,
  c_create_date      timestamp with time zone not null,
  c_last_access_date timestamp with time zone not null,
  c_token            character varying(255)   not null,
  c_ip               character varying(255),
  c_device_id        character varying(255)   not null,
  fk_account         bigint                   not null
);

create sequence sec.s_oauth_token
  start with 1
  increment by 50;

--constraints--
--primary key
alter table sec.t_oauth_token
  add constraint pkc_oauth_token primary key (pk_id);

--unique
alter table sec.t_oauth_token
  add constraint uc_oauth_token_token unique (c_token);

--foreign key
alter table sec.t_oauth_token
  add constraint fkc_oauth_token_user foreign key (fk_account) references sec.t_account (pk_id);
