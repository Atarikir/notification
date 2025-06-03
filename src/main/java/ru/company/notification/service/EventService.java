package ru.company.notification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private final NotificationService notificationService;

    @Transactional
    public EventResponse createEvent(EventRequest eventRequest) {
        Event newEvent = eventMapper.toEvent(eventRequest);
        eventRepository.save(newEvent);
        notificationService.saveEventNotification(newEvent);
        return eventMapper.toEventResponse(newEvent);
    }

    @Transactional(readOnly = true)
    public List<EventResponse> getAllEvents() {
        // при необходимости сделать пагинацию
        return eventMapper.toList(eventRepository.findAll());
    }
}
