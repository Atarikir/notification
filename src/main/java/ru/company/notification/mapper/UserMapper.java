package ru.company.notification.mapper;

import org.mapstruct.*;
import ru.company.notification.dto.NotificationPeriodDto;
import ru.company.notification.dto.request.UserRequest;
import ru.company.notification.dto.response.UserResponse;
import ru.company.notification.model.NotificationPeriod;
import ru.company.notification.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = NotificationPeriodMapper.class)
public interface UserMapper {

    @Mapping(target = "notificationPeriods", ignore = true)
    User toUser(UserRequest request);

    @Mapping(target = "notificationPeriods", ignore = true)
    void updateUserFromRequest(UserRequest request, @MappingTarget User user);

    @AfterMapping
    default void mapNotificationPeriods(@MappingTarget User user, UserRequest request) {
        if (request.getNotificationPeriods() != null) {
            List<NotificationPeriod> newPeriods = request.getNotificationPeriods().stream()
                    .map(dto -> NotificationPeriod.builder()
                            .dayOfWeek(dto.dayOfWeek())
                            .startTime(dto.startTime())
                            .endTime(dto.endTime())
                            .user(user)
                            .build())
                    .collect(Collectors.toList());

            if (user.getNotificationPeriods() == null) {
                user.setNotificationPeriods(newPeriods);
            } else {
                user.getNotificationPeriods().clear();
                user.getNotificationPeriods().addAll(newPeriods);
            }
        }
    }

    UserResponse toUserResponse(User user);

    List<UserResponse> toList(List<User> users);
}
