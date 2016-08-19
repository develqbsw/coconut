--create tables and sequence
CREATE TABLE sec.t_oauth_token
(
  pk_id bigint NOT NULL,
  d_type character varying(31) NOT NULL,
  c_create_date timestamp with time zone NOT NULL,
  c_last_access_date timestamp with time zone NOT NULL,
  c_token character varying(255) NOT NULL,
  c_ip character varying(255),
  c_device_id character varying(255) NOT NULL,
  fk_user bigint NOT NULL
);

CREATE SEQUENCE sec.S_OAUTH_TOKEN START WITH 1 INCREMENT BY 1;

--constraints--
--primary key
ALTER TABLE sec.t_oauth_token
    ADD CONSTRAINT pk_oauth_token PRIMARY KEY (pk_id);
    
--unique
ALTER TABLE sec.t_oauth_token
    ADD CONSTRAINT uk_oauth_token_token UNIQUE (c_token);

--foreign key
ALTER TABLE sec.t_oauth_token
    ADD CONSTRAINT fk_oauth_token_user FOREIGN KEY (fk_user) REFERENCES sec.t_user (pk_id);