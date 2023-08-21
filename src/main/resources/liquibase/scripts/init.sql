--liquibase formatted sql

--changeset sbukaevsky:1
create table users
(
    id         bigserial primary key unique,
    first_name varchar(30) not null,
    last_name  varchar(50) not null,
    username   varchar(15) not null unique,
    email      varchar     not null unique,
    password   varchar     not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp,
    role       varchar(32) not null
);

create table user_subscriptions
(
    subscriber_id bigserial not null references users (id),
    channel_id    bigserial not null references users (id),
    primary key (subscriber_id, channel_id)
);

create table images
(
    id         bigserial primary key unique,
    file_path  varchar not null,
    file_size  bigint  NOT NULL,
    media_type varchar not null,
    data       bytea   NOT NULL,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp,
    post_id    bigserial
);

create table messages
(
    id               bigserial primary key unique,
    text             varchar(500) not null,
    sender_msg_id    bigserial references users (id),
    recipient_msg_id bigserial references users (id),
    created_at       timestamp default current_timestamp
);

create table posts
(
    id         bigserial primary key unique,
    title      varchar(20)   not null,
    text       varchar(2000) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp,
    user_id    bigserial
);

create table subscriptions
(
    id               bigserial primary key unique,
    sender_app_id    bigserial references users (id),
    recipient_app_id bigserial references users (id),
    created_at       timestamp default current_timestamp,
    updated_at       timestamp default current_timestamp,
    status           VARCHAR(32) NOT NULL
);