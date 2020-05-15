create table account_project
(
    account_id varchar(255) not null references account (id),
    project_id int8         not null references project (id),
    primary key (account_id, project_id)
);