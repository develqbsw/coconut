--The file removes the unneccessary database data

DELETE FROM sec.t_auth_params as a WHERE a.pk_id = 1;
DELETE FROM sec.t_auth_params as a WHERE a.pk_id = 2;
DELETE FROM sec.t_user as u WHERE u.pk_id = 1;
DELETE FROM sec.t_user as u WHERE u.pk_id = 2;
DELETE FROM sec.t_organization as o WHERE o.pk_id = 1;