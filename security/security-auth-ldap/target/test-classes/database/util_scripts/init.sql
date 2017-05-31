GRANT USAGE ON SCHEMA sec TO coconut_gw;

GRANT USAGE, SELECT ON SEQUENCE sec.t_auth_params_pk_id_seq TO coconut_gw;
GRANT USAGE, SELECT ON SEQUENCE sec.t_group_pk_id_seq TO coconut_gw;
GRANT USAGE, SELECT ON SEQUENCE sec.t_licence_pk_id_seq TO coconut_gw;
GRANT USAGE, SELECT ON SEQUENCE sec.t_organization_pk_id_seq TO coconut_gw;
GRANT USAGE, SELECT ON SEQUENCE sec.t_role_pk_id_seq TO coconut_gw;
GRANT USAGE, SELECT ON SEQUENCE sec.t_unit_pk_id_seq TO coconut_gw;
GRANT USAGE, SELECT ON SEQUENCE sec.t_user_pk_id_seq TO coconut_gw;
GRANT USAGE, SELECT ON SEQUENCE sec.t_x_group_user_pk_id_seq TO coconut_gw;

GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA sec TO coconut_gw;