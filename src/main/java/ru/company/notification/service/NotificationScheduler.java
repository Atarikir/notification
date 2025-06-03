package ru.company.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.company.notification.model.Event;
import ru.company.notification.model.Notification;
import ru.company.notification.model.NotificationPeriod;
import ru.company.notification.model.User;
import ru.company.notification.repository.NotificationRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static ru.company.notification.util.TimeUtil.getNearestPeriod;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationScheduler {

    private final NotificationRepository notificationRepository;

    @Scheduled(fixedRate = 60000)
    public void processingPendingNotifications() {
        calculateNotificationTimes(); // Пересчитываем время для уведомлений
        sendNotifications(); // Отправляем уведомления, время которых наступило
    }

    private void calculateNotificationTimes() {
        List<Notification> notifications = notificationRepository.findBySentFalseAndSendingTimeNull();
        for (Notification notification : notifications) {
            LocalDateTime calculatedTime = calculateNearestSendTime(notification.getUser(), notification.getEvent());
            notification.setSendingTime(calculatedTime);
            notificationRepository.save(notification);
        }
    }

    private LocalDateTime calculateNearestSendTime(User user, Event event) {
        LocalDateTime eventTime = event.getOccurrenceTime();

        // Проверяем, попадает ли время события в текущие периоды
        for (NotificationPeriod period : user.getNotificationPeriods()) {
            if (period.getDayOfWeek() == eventTime.getDayOfWeek()
                    && period.getStartTime().isBefore(eventTime.toLocalTime())
                    && period.getEndTime().isAfter(eventTime.toLocalTime())) {
                return eventTime;
            }
        }

        // Ищем ближайший период в будущем
        LocalDateTime earliestPossible = getNearestPeriod(user, eventTime);
        return earliestPossible != null ? earliestPossible : eventTime;
    }

    private void sendNotifications() {
        List<Notification> notifications = notificationRepository.findBySentFalseAndSendingTimeBeforeNow();
        for (Notification notification : notifications) {
            sendNotification(notification);
        }
    }

    private void sendNotification(Notification notification) {
        String logMessage = String.format("%s Пользователю %s отправлено оповещение с текстом: %s",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")),
                notification.getUser().getFullName(),
                notification.getEvent().getMessage());
        log.info(logMessage);
        notification.setSent(true);
        notificationRepository.save(notification);
    }
}
