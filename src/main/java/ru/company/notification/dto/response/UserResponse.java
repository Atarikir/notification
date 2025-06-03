package ru.company.notification.dto.response;

import ru.company.notification.dto.NotificationPeriodDto;

import java.util.List;
import java.util.UUID;

public record UserResponse(UUID id, String fullName, List<NotificationPeriodDto> notificationPeriods) {}
