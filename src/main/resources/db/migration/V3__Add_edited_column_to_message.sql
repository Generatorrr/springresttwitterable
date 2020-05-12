alter table message
add column edited boolean default false;

alter table message_aud
    add column edited boolean default false;