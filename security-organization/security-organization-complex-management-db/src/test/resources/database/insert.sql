insert into org.t_user (pk_id, d_type, c_state)
    values (1, 'user', 'ACTIVE');
insert into org.t_user (pk_id, d_type, c_state)
    values (2, 'user', 'ACTIVE');

insert into org.t_organization (pk_id, d_type, c_code, c_name, c_email, c_state)
    values (1, 'organizationComplex', 'unit_test_organization_complex', 'unit_test_organization_complex', 'unit_test_organization_complex@qbsw.sk', 'ACTIVE');
insert into org.t_organization (pk_id, d_type, c_code, c_name, c_email, c_state)
    values (2, 'organizationComplex', 'unit_test_organization_complex_disabled', 'unit_test_organization_complex_disabled', 'unit_test_organization_complex_disabled@qbsw.sk', 'INACTIVE');

insert into sec.t_organization (pk_id, d_type, c_code, c_name, c_email, c_state)
    values (1, 'organization', 'unit_test_organization', 'unit_test_organization', 'unit_test_organization@qbsw.sk', 'ACTIVE');
