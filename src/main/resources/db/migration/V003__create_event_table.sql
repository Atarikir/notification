CREATE TABLE IF NOT EXISTS events (
    id              uuid         PRIMARY KEY,
    message         varchar(255) NOT NULL,
    occurrence_time timestamp    NOT NULL
);

COMMENT ON TABLE events IS 'События';
COMMENT ON COLUMN events.id IS 'Идентификатор события';
COMMENT ON COLUMN events.message IS 'Сообщение события';
COMMENT ON COLUMN events.occurrence_time IS 'Время появления события';