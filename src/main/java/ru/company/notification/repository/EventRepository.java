package ru.company.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.company.notification.model.Event;

import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {}
