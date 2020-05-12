create table account_subscriptions
(
    subscriber_id varchar(255) not null references account,
    channel_id    varchar(255) not null references account,
    primary key (channel_id, subscriber_id)
);

create table account_subscriptions_aud
(
    subscriber_id varchar(255) not null references account,
    channel_id    varchar(255) not null references account,
    primary key (channel_id, subscriber_id),
    rev integer not null ,
    revtype smallint
);