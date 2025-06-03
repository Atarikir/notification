CREATE TABLE IF NOT EXISTS notification_periods (
    id          uuid        PRIMARY KEY,
    day_of_week varchar(10) NOT NULL,
    start_time  time        NOT NULL,
    end_time    time        NOT NULL,
    user_id     uuid        NOT NULL,
    CONSTRAINT fk_user_notification_period FOREIGN KEY (user_id) REFERENCES users (id)
);

COMMENT ON TABLE notification_periods IS 'Периоды оповещения пользователей';
COMMENT ON COLUMN notification_periods.id IS 'Идентификатор периода';
COMMENT ON COLUMN notification_periods.day_of_week IS 'День недели';
COMMENT ON COLUMN notification_periods.start_time IS 'Время начала периода';
COMMENT ON COLUMN notification_periods.end_time IS 'Время конца периода';
COMMENT ON COLUMN notification_periods.user_id IS 'Идентификатор пользователя';