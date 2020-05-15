create table account_module
(
    account_id varchar(255) not null references account (id),
    module_id int8         not null references module (id),
    primary key (account_id, module_id)
);

create table account_test_plan
(
    account_id varchar(255) not null references account (id),
    test_plan_id int8         not null references test_plan (id),
    primary key (account_id, test_plan_id)
);

create table account_requirement
(
    account_id varchar(255) not null references account (id),
    requirement_id int8         not null references requirement (id),
    primary key (account_id, requirement_id)
);

create table account_test_case
(
    account_id varchar(255) not null references account (id),
    test_case_id int8         not null references test_case (id),
    primary key (account_id, test_case_id)
);

create table account_task
(
    account_id varchar(255) not null references account (id),
    task_id int8         not null references task (id),
    primary key (account_id, task_id)
);

create table account_check_list
(
    account_id varchar(255) not null references account (id),
    check_list_id int8         not null references check_list (id),
    primary key (account_id, check_list_id)
);