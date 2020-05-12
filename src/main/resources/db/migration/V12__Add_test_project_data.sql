insert into account(id, name, email, last_visit)
values ('asd', 'test user 1', 'azaza@gmail.com', now());

insert into account(id, name, email, last_visit)
values ('qwe', 'test user 2', 'ololo@gmail.com', now());

insert into account_role(account_id, roles)
values ('asd', 'ADMIN');

insert into account_role(account_id, roles)
values ('qwe', 'USER');

insert into project(id, name, description, status, init_date, end_date, created_on, updated_on, created_by, updated_by, project_owner)
values (1, 'test project 1', 'project for blablabla 1', 'TO_DO', now(), now(), now(), now(), 'qwe', 'qwe', 'qwe');

insert into project(id, name, description, status, init_date, end_date, created_on, updated_on, created_by, updated_by, project_owner)
values (2, 'test project 2', 'project for blablabla 2', 'TO_DO', now(), now(), now(), now(), 'asd', 'asd', 'asd');