package ru.company.notification.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import ru.company.notification.dto.NotificationPeriodDto;

import java.util.List;

public record UserRequest(@NotNull(message = "Fill in the full name")
                          @Size(min = 3, message = "Full name can't be less than 3")
                          String fullName,
                          @Getter List<NotificationPeriodDto> notificationPeriods) {}
