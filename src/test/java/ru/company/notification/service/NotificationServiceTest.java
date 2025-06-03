package ru.company.notification.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.company.notification.model.Event;
import ru.company.notification.model.Notification;
import ru.company.notification.model.User;
import ru.company.notification.repository.NotificationRepository;
import ru.company.notification.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentCaptor.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private NotificationRepository notificationRepository;
    @InjectMocks
    private NotificationService notificationService;
    private Event event;
    private User user1;
    private User user2;
    private List<User> users;

    @BeforeEach
    void setUp() {
        event = new Event();
        event.setId(UUID.randomUUID());

        user1 = new User();
        user1.setId(UUID.randomUUID());
        user2 = new User();
        user2.setId(UUID.randomUUID());
        users = List.of(user1, user2);
    }

    @AfterEach
    void tearDown() {
        event = null;
        user1 = null;
        user2 = null;
        users = null;
    }

    @Test
    void saveEventNotification_ShouldCreateNotificationsForAllUsers() {
        when(userRepository.findAllWithPeriods()).thenReturn(users);

        notificationService.saveEventNotification(event);

        verify(userRepository).findAllWithPeriods();
        verify(notificationRepository).saveAll(anyList());

        ArgumentCaptor<List<Notification>> notificationsCaptor = forClass(List.class);
        verify(notificationRepository).saveAll(notificationsCaptor.capture());

        List<Notification> savedNotifications = notificationsCaptor.getValue();
        assertEquals(2, savedNotifications.size());
        assertEquals(user1, savedNotifications.get(0).getUser());
        assertEquals(event, savedNotifications.get(0).getEvent());
        assertEquals(user2, savedNotifications.get(1).getUser());
        assertEquals(event, savedNotifications.get(1).getEvent());
    }

    @Test
    void saveEventNotification_WithNoUsers_ShouldNotCreateNotifications() {
        when(userRepository.findAllWithPeriods()).thenReturn(Collections.emptyList());

        notificationService.saveEventNotification(event);

        verify(userRepository).findAllWithPeriods();
        verify(notificationRepository, never()).saveAll(anyList());
    }

    @Test
    void markSendingTimeNull_WithUnsentNotifications_ShouldUpdateNotifications() {
        Notification notification1 = new Notification();
        notification1.setId(UUID.randomUUID());
        Notification notification2 = new Notification();
        notification2.setId(UUID.randomUUID());

        when(notificationRepository.findAllByUserAndSentFalse(user1)).thenReturn(List.of(notification1, notification2));

        notificationService.markSendingTimeNull(user1);

        verify(notificationRepository).findAllByUserAndSentFalse(user1);
        verify(notificationRepository).markSendingTimeNullByUserId(user1.getId());
    }

    @Test
    void markSendingTimeNull_WithNoUnsentNotifications_ShouldNotUpdate() {
        when(notificationRepository.findAllByUserAndSentFalse(user2)).thenReturn(Collections.emptyList());

        notificationService.markSendingTimeNull(user2);

        verify(notificationRepository).findAllByUserAndSentFalse(user2);
        verify(notificationRepository, never()).markSendingTimeNullByUserId(any(UUID.class));
    }
}