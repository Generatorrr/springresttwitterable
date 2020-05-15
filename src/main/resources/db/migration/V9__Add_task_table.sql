create table task
(
    id           int8          not null,
    name         varchar(255)  not null,
    description  varchar(2000) not null,
    status       varchar(20)   not null,
    task_type    varchar(40)   not null,
    severity     varchar(20)   not null,
    init_date    timestamp     not null,
    end_date     timestamp     not null,
    created_on   timestamp     not null,
    updated_on   timestamp     not null,
    created_by   varchar(255) references account (id) on delete restrict,
    updated_by   varchar(255) references account (id) on delete restrict,
    test_case_id int8          not null references test_case (id) on delete cascade,
    primary key (id)
);