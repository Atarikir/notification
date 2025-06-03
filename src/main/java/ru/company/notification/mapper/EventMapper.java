package ru.company.notification.mapper;

import org.mapstruct.Mapper;
import ru.company.notification.dto.request.EventRequest;
import ru.company.notification.dto.response.EventResponse;
import ru.company.notification.model.Event;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {

    Event toEvent(EventRequest request);

    EventResponse toEventResponse(Event event);

    List<EventResponse> toList(List<Event> events);
}
