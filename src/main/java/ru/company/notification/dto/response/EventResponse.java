package ru.company.notification.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record EventResponse(UUID id, String message, LocalDateTime occurrenceTime) {}
