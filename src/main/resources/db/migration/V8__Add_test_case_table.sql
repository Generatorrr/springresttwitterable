create table test_case
(
    id             int8          not null,
    name           varchar(255)  not null,
    description    varchar(2000) not null,
    status         varchar(20)   not null,
    test_case      varchar(2000) not null,
    init_date      timestamp     not null,
    end_date       timestamp     not null,
    created_on     timestamp     not null,
    updated_on     timestamp     not null,
    created_by     varchar(255) references account (id) on delete restrict,
    updated_by     varchar(255) references account (id) on delete restrict,
    requirement_id int8          not null references requirement (id) on delete cascade,
    primary key (id)
);