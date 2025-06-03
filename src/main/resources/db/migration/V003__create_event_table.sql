CREATE TABLE IF NOT EXISTS events (
    id              uuid         PRIMARY KEY,
    message         varchar(255) NOT NULL,
    occurrence_time timestamp    NOT NULL
);