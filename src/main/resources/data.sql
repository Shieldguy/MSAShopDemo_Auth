insert into user_role (name, role_type) values ('master', 0);
insert into user_role (name, role_type) values ('user', 4);

insert into auth_user (username, password, enabled) values ('test', 'test1234', true);

insert into map_authuser_role (authuser_id, role_id) values (1, 1);
insert into map_authuser_role (authuser_id, role_id) values (1, 2);
