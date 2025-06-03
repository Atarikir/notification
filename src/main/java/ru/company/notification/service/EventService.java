package ru.company.notification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.company.notification.dto.request.EventRequest;
import ru.company.notification.dto.response.EventResponse;
import ru.company.notification.mapper.EventMapper;
import ru.company.notification.model.Event;
import ru.company.notification.repository.EventRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventMapper eventMapper;
    private final EventRepository eventRepository;

    public EventResponse createEvent(EventRequest eventRequest) {
        Event newEvent = eventMapper.toEvent(eventRequest);
        eventRepository.save(newEvent);

        // Обработка события для создания уведомлений

        return eventMapper.toEventResponse(newEvent);
    }

    public List<EventResponse> getAllEvents() {
        // при необходимости сделать пагинацию
        return eventMapper.toList(eventRepository.findAll());
    }
}
