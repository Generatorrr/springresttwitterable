create sequence hibernate_sequence start 1 increment 1;

create table message (
  id int8 not null,
  filename varchar(255),
  tag varchar(255),
  text varchar(2048) not null,
  user_id varchar(255),
  primary key (id)
);

create table account_role (
  account_id varchar(255) not null,
  roles varchar(255)
);

create table account (
  id varchar(255) not null,
  name varchar(255) not null,
  userpic varchar(255),
  gender varchar(255),
  locale varchar(255),
  email varchar(255) not null,
  last_visit timestamp not null,
  primary key (id)
);

alter table message
  add constraint message_account_fk
  foreign key (user_id) references account (id);

alter table account_role
  add constraint account_role_account_fk
  foreign key (account_id) references account (id);