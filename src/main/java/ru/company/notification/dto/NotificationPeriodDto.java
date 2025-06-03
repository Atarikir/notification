package ru.company.notification.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record NotificationPeriodDto(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {}
