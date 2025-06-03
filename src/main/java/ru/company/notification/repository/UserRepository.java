package ru.company.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.company.notification.model.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {}
