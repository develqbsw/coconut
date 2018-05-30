GRANT USAGE ON SCHEMA sec TO coconut_gw;

GRANT USAGE, SELECT ON SEQUENCE sec.s_auth_paramsTO coconut_gw;
GRANT USAGE, SELECT ON SEQUENCE sec.s_group TO coconut_gw;
GRANT USAGE, SELECT ON SEQUENCE sec.s_organization TO coconut_gw;
GRANT USAGE, SELECT ON SEQUENCE sec.s_role TO coconut_gw;
GRANT USAGE, SELECT ON SEQUENCE sec.s_unit TO coconut_gw;
GRANT USAGE, SELECT ON SEQUENCE sec.s_account TO coconut_gw;
GRANT USAGE, SELECT ON SEQUENCE sec.s_x_account_unit_group TO coconut_gw;

GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA sec TO coconut_gw;
