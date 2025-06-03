CREATE TABLE IF NOT EXISTS notification_periods (
    id          uuid        PRIMARY KEY,
    day_of_week varchar(10) NOT NULL,
    start_time  time        NOT NULL,
    end_time    time        NOT NULL,
    user_id     uuid        NOT NULL,
    CONSTRAINT fk_user_notification_period FOREIGN KEY (user_id) REFERENCES users (id)
);