CREATE TABLE IF NOT EXISTS users (
    id        uuid         PRIMARY KEY,
    full_name varchar(255) NOT NULL,
    CONSTRAINT user_id_full_name_unique UNIQUE (full_name, id)
);

COMMENT ON TABLE users IS 'Пользователи';
COMMENT ON COLUMN users.id IS 'Идентификатор пользователя';
COMMENT ON COLUMN users.full_name IS 'ФИО';