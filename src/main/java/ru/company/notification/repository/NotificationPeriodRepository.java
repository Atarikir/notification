package ru.company.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.company.notification.model.NotificationPeriod;

import java.util.UUID;

public interface NotificationPeriodRepository extends JpaRepository<NotificationPeriod, UUID> {}
