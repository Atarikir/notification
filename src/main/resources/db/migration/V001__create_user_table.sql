CREATE TABLE IF NOT EXISTS users (
    id        uuid         PRIMARY KEY,
    full_name varchar(255) NOT NULL,
    CONSTRAINT user_id_full_name_unique UNIQUE (full_name, id)
);