insert into test_case(id, name, description, status, test_case, init_date, end_date, created_on, updated_on, created_by, updated_by, requirement_id)
values (1, 'test case 1', 'test case for feature 11', 'TO_DO', 'check that all work fine 1', now(), now(), now(), now(), 'qwe', 'qwe', 1);

insert into test_case(id, name, description, status, test_case, init_date, end_date, created_on, updated_on, created_by, updated_by, requirement_id)
values (2, 'test case 2', 'test case for feature 22', 'TO_DO', 'check that all work fine 2', now(), now(), now(), now(), 'qwe', 'qwe', 1);

insert into test_case(id, name, description, status, test_case, init_date, end_date, created_on, updated_on, created_by, updated_by, requirement_id)
values (3, 'test case 3', 'test case for feature 33', 'TO_DO', 'check that all work fine 3', now(), now(), now(), now(), 'qwe', 'qwe', 1);

insert into task(id, name, description, status, task_type, severity, init_date, end_date, created_on, updated_on, created_by, updated_by, test_case_id)
values (1, 'task 1', 'CR for more beautiful UI', 'TO_DO', 'CHANGE_REQUEST', 'MAJOR', now(), now(), now(), now(), 'qwe', 'qwe', 1);

insert into task(id, name, description, status, task_type, severity, init_date, end_date, created_on, updated_on, created_by, updated_by, test_case_id)
values (2, 'lol bagok', 'pfffff', 'TO_DO', 'BUG', 'BLOCKER', now(), now(), now(), now(), 'qwe', 'qwe', 1);

insert into task(id, name, description, status, task_type, severity, init_date, end_date, created_on, updated_on, created_by, updated_by, test_case_id)
values (3, 'debik task', 'description azaza', 'TO_DO', 'IMPROVEMENT', 'TRIVIAL', now(), now(), now(), now(), 'qwe', 'qwe', 1);