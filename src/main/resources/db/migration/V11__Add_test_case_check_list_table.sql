create table test_case_check_list
(
    test_case_id    int8    not null references test_case (id) on delete restrict,
    check_list_id   int8    not null references check_list (id) on delete restrict,
    test_case_order integer not null,
    created_on   timestamp     not null,
    updated_on   timestamp     not null,
    created_by   varchar(255) references account (id) on delete restrict,
    updated_by   varchar(255) references account (id) on delete restrict,
    primary key (test_case_id, check_list_id)
);

create table test_case_check_list_aud
(
    test_case_id    int8    not null references test_case (id) on delete restrict,
    check_list_id   int8    not null references check_list (id) on delete restrict,
    test_case_order integer not null,
    created_on   timestamp     not null,
    updated_on   timestamp     not null,
    created_by   varchar(255) references account (id) on delete restrict,
    updated_by   varchar(255) references account (id) on delete restrict,
    rev             integer not null,
    revtype         smallint,
    primary key (test_case_id, check_list_id)
);