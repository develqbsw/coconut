--The file removes the unneccessary database data from performance test

--create organization
INSERT INTO sec.t_organization (type, pk_id, c_change_date_time, code, email, flag_enabled, name, phone, fk_address, fax) VALUES ('organization', 1, '2014-01-01 00:00:00', 'unit_test_organization', NULL, true, 'unit_test_organization', NULL, NULL, NULL);
--create user
INSERT INTO sec.t_user (pk_id, c_change_date_time, email, flag_enabled, login, name, surname, type, fk_address, fk_default_unit, fk_organization, degree, working_position) VALUES (1, '2014-01-01 00:00:00', 'unit_test_user_with_default_unit@qbsw.sk', true, 'unit_test_user_with_default_unit', 'test', 'test', NULL, NULL, NULL, 1, NULL, NULL);
INSERT INTO sec.t_user (pk_id, c_change_date_time, email, flag_enabled, login, name, surname, type, fk_address, fk_default_unit, fk_organization, degree, working_position) VALUES (2, '2014-01-01 00:00:00', 'unit_test_user_with_default_unit_no_group@qbsw.sk', true, 'unit_test_user_with_default_unit_no_group', 'test1', 'test1', NULL, NULL, NULL, 1, NULL, NULL);
--create auth params
INSERT INTO sec.t_auth_params (pk_id, c_change_date_time, password, passworddigest, pin, fk_user, c_password_type) VALUES (1, '2014-01-01 00:00:00', 'unit_test_user_with_default_unit', NULL, NULL, 1, 'DURABLE');
INSERT INTO sec.t_auth_params (pk_id, c_change_date_time, password, passworddigest, pin, fk_user, c_password_type) VALUES (2, '2014-01-01 00:00:00', 'unit_test_user_with_default_unit_no_group', NULL, NULL, 2, 'DURABLE');