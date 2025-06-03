package ru.company.notification.util;

import ru.company.notification.model.NotificationPeriod;
import ru.company.notification.model.User;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class TimeUtil {

    public static LocalDateTime getNearestPeriod(User user, LocalDateTime eventTime) {
        LocalDateTime currentCheck = eventTime;
        LocalDateTime earliestPossible = null;

        // Проверяем 7 дней вперед
        for (int i = 0; i < 7; i++) {
            currentCheck = currentCheck.plusDays(1);
            DayOfWeek checkDay = currentCheck.getDayOfWeek();
            for (NotificationPeriod period : user.getNotificationPeriods()) {
                if (period.getDayOfWeek() == checkDay) {
                    LocalDateTime candidate = LocalDateTime.of(currentCheck.toLocalDate(), period.getStartTime());
                    if (earliestPossible == null || candidate.isBefore(earliestPossible)) {
                        earliestPossible = candidate;
                    }
                }
            }
        }
        return earliestPossible;
    }
}
