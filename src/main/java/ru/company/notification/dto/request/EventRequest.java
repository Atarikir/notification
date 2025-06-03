package ru.company.notification.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record EventRequest(@NotNull(message = "Fill in the service name")
                           @Size(min = 1, message = "Service name can't be less than 1")
                           String message,
                           @NotNull(message = "Fill in the occurrence time")
                           LocalDateTime occurrenceTime) {}
