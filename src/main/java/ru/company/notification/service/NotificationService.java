package ru.company.notification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.company.notification.model.Event;
import ru.company.notification.model.Notification;
import ru.company.notification.model.User;
import ru.company.notification.repository.NotificationRepository;
import ru.company.notification.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;

    @Transactional
    public void saveEventNotification(Event event) {
        List<User> users = userRepository.findAllWithPeriods();
        List<Notification> notifications = new ArrayList<>();
        users.forEach(user -> {
            Notification notification = Notification.builder()
                    .user(user)
                    .event(event)
                    .build();
            notifications.add(notification);
        });
        notificationRepository.saveAll(notifications);
    }

    @Transactional
    public void markSendingTimeNull(User user) {
        List<Notification> notifications = notificationRepository.findAllByUserAndSentFalse(user);
        if (!notifications.isEmpty()) {
            notificationRepository.markSendingTimeNullByUserId(user.getId());
        }
    }
}
