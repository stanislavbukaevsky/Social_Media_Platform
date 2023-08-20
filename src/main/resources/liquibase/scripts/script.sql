--liquibase formatted sql

--changeset sbukaevsky:1
CREATE TABLE users
(
    id         SERIAL PRIMARY KEY,
    first_name VARCHAR(64)   NOT NULL,
    last_name  VARCHAR(64)   NOT NULL,
    username   VARCHAR(64)   NOT NULL UNIQUE,
    email      VARCHAR(64)   NOT NULL UNIQUE,
    password   VARCHAR(2048) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    role       VARCHAR(32)   NOT NULL
)

--changeset sbukaevsky:2
CREATE TABLE posts
(
    id         SERIAL PRIMARY KEY,
    title      VARCHAR,
    text       VARCHAR,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
)

--changeset sbukaevsky:3
CREATE TABLE images
(
    id         SERIAL PRIMARY KEY,
    file_path  VARCHAR NOT NULL,
    file_size  SERIAL  NOT NULL,
    media_type VARCHAR NOT NULL,
    data       BYTEA   NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
)