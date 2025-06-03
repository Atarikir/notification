package ru.company.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.company.notification.model.User;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("SELECT DISTINCT u FROM User u JOIN FETCH u.notificationPeriods")
    List<User> findAllWithPeriods();
}
