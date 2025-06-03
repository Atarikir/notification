CREATE TABLE IF NOT EXISTS notifications (
    id           uuid      PRIMARY KEY,
    user_id      uuid      NOT NULL,
    event_id     uuid      NOT NULL,
    sending_time timestamp,
    sent         boolean   NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_user_notification FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_event_notification FOREIGN KEY (event_id) REFERENCES events (id)
);

COMMENT ON TABLE notifications IS 'Оповещения пользователей';
COMMENT ON COLUMN notifications.id IS 'Идентификатор оповещения';
COMMENT ON COLUMN notifications.user_id IS 'Идентификатор пользователя';
COMMENT ON COLUMN notifications.event_id IS 'Идентификатор события';
COMMENT ON COLUMN notifications.sending_time IS 'Время отправки оповещения пользователю';
COMMENT ON COLUMN notifications.sent IS 'Флаг, показывает отправлено оповещение или нет';