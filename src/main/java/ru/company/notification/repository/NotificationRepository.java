package ru.company.notification.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.company.notification.model.Notification;
import ru.company.notification.model.User;

import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {

    List<Notification> findAllByUserAndSentFalse(User user);

    @EntityGraph(attributePaths = {"user", "user.notificationPeriods", "event"})
    @Query("SELECT n FROM Notification n WHERE n.sent = false AND n.sendingTime is null")
    List<Notification> findBySentFalseAndSendingTimeNull();

    @EntityGraph(attributePaths = {"user", "event"})
    @Query("SELECT n FROM Notification n WHERE n.sent = false AND n.sendingTime <= CURRENT_TIMESTAMP")
    List<Notification> findBySentFalseAndSendingTimeBeforeNow();

    @Modifying
    @Query("UPDATE Notification n SET n.sendingTime = null WHERE n.user.id = :userId AND n.sent = false")
    void markSendingTimeNullByUserId(@Param("userId") UUID userId);
}
