package ru.company.notification.mapper;

import org.mapstruct.*;
import ru.company.notification.dto.request.UserRequest;
import ru.company.notification.dto.response.UserResponse;
import ru.company.notification.model.NotificationPeriod;
import ru.company.notification.model.User;

import java.util.List;

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
                    .toList();
            user.getNotificationPeriods().clear();
            user.getNotificationPeriods().addAll(newPeriods);
        } else {
            user.getNotificationPeriods().clear();
        }
    }

    UserResponse toUserResponse(User user);

    List<UserResponse> toList(List<User> users);
}
