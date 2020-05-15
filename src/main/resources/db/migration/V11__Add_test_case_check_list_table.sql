create table test_case_check_list
(
    test_case_id    int8          not null references test_case (id) on delete restrict,
    check_list_id   int8          not null references check_list (id) on delete restrict,
    name            varchar(255)  not null,
    description     varchar(2000) not null,
    status          varchar(20)   not null,
    test_case_order integer       not null,
    created_on      timestamp     not null,
    updated_on      timestamp     not null,
    created_by      varchar(255) references account (id) on delete restrict,
    updated_by      varchar(255) references account (id) on delete restrict,
    primary key (test_case_id, check_list_id)
);