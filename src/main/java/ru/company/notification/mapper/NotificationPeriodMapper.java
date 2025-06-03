package ru.company.notification.mapper;

import org.mapstruct.Mapper;
import ru.company.notification.dto.NotificationPeriodDto;
import ru.company.notification.model.NotificationPeriod;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificationPeriodMapper {

    NotificationPeriod toEntity(NotificationPeriodDto dto);

    List<NotificationPeriod> toList(List<NotificationPeriodDto> dtos);
}
