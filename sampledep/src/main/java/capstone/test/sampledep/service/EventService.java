package capstone.test.sampledep.service;

import capstone.test.sampledep.data.Event;
import capstone.test.sampledep.data.EventType;
import capstone.test.sampledep.data.Participation;
import capstone.test.sampledep.data.User;
import capstone.test.sampledep.pojo.ParticipationType;
import capstone.test.sampledep.pojo.Scope;
import capstone.test.sampledep.repository.EventRepository;
import capstone.test.sampledep.repository.EventTypeRepository;
import capstone.test.sampledep.repository.ParticipationRepository;
import capstone.test.sampledep.repository.UserRepository;
import capstone.test.sampledep.request.CreateEventRequest;
import capstone.test.sampledep.response.GetEventResponse;
import capstone.test.sampledep.response.UserEventsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.Part;
import javax.transaction.Transactional;
import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ParticipationRepository participationRepository;

    @Autowired
    private ParticipationService participationService;

    @Autowired
    private EventTypeRepository eventTypeRepository;

    @Transactional
    public Event createEvent(Long userId, CreateEventRequest request) throws Exception{
        if (Objects.isNull(userId))
            throw new Exception("userId of host cannot be null");
        if (StringUtils.isEmpty(request.getName()) || StringUtils.isEmpty(request.getLocation()) || StringUtils.isEmpty(request.getEventType()) || StringUtils.isEmpty(request.getScope()))
            throw new Exception("Event Name, Location, Event Type, Scope cannot be empty");
        Event event = new Event();
        event.setName(request.getName());
        event.setEventType(eventTypeRepository.findByType(request.getEventType()));
        event.setDescription(request.getDescription());
        event.setLocation(request.getLocation());
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(request.getTime()), ZoneId.systemDefault());
        event.setTime(localDateTime);
        event.setLimit(request.getLimit());
        event.setScope(Scope.findScopeByKey(request.getScope()));
        Event savedEvent = eventRepository.save(event);
        Participation participation = participationService.createParticipationByHost(userId, savedEvent);
        return savedEvent;
    }

    public Event updateEvent(Long userId, Long eventId, CreateEventRequest request) throws Exception{
        if (Objects.isNull(userId))
            throw new Exception("userId of host cannot be null");
        if (StringUtils.isEmpty(request.getName()) || StringUtils.isEmpty(request.getLocation()) || StringUtils.isEmpty(request.getEventType()) || StringUtils.isEmpty(request.getScope()))
            throw new Exception("Event Name, Location, Event Type, Scope cannot be empty");
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        if (!optionalEvent.isPresent())
            throw new Exception("No Such Event with Id : " + eventId);
        Event event = optionalEvent.get();
        Participation participation = participationRepository.findByUser_IdAndEvent_Id(userId, eventId);
        if (Objects.isNull(participation) || !Objects.equals(participation.getParticipationType(), ParticipationType.HOST))
            throw new Exception("Only the host of this event can edit it ");
        event.setName(request.getName());
        event.setEventType(eventTypeRepository.findByType(request.getEventType()));
        event.setDescription(request.getDescription());
        event.setLocation(request.getLocation());
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(request.getTime()), ZoneId.systemDefault());
        event.setTime(localDateTime);
        event.setLimit(request.getLimit());
        event.setScope(Scope.findScopeByKey(request.getScope()));
        Event savedEvent = eventRepository.save(event);
        return savedEvent;
    }

    public Participation joinEvent(Long userId, Long eventId) throws Exception{
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        if (!optionalEvent.isPresent())
            throw new Exception("No such event with id : " + eventId);
        if (!optionalUser.isPresent())
            throw new Exception(("No such user with id : " + userId));
        Participation participation = participationService.createParticipationByAttendee(userId, optionalEvent.get());
        return participation;
    }

    public UserEventsResponse getUserEvents(Long userId) {
        List<Long> hostedEvents = participationRepository.findByUser_IdAndParticipationType(userId, ParticipationType.HOST)
                .stream().map(Participation::getEvent).map(Event::getId).collect(Collectors.toList());
        List<Long> joinedEvents = participationRepository.findByUser_IdAndParticipationType(userId, ParticipationType.ATTENDEE)
                .stream().map(Participation::getEvent).map(Event::getId).collect(Collectors.toList());
        UserEventsResponse userEventsResponse = new UserEventsResponse();
        userEventsResponse.setHosted(hostedEvents);
        userEventsResponse.setJoined(joinedEvents);
        return userEventsResponse;
    }

    public GetEventResponse getEvent(Long userId, Long eventId) throws Exception{
        GetEventResponse response = new GetEventResponse();
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        if (!optionalEvent.isPresent())
            throw new Exception("No event with id : " + eventId);

        Event event = optionalEvent.get();
        response.setName(event.getName());
        response.setDescription(event.getDescription());
        response.setLocation(event.getLocation());
        response.setTime(event.getTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        response.setEventType(event.getEventType().getType());
        response.setScope(event.getScope().getKey());
        response.setLimit(event.getLimit());
        List<Participation> participationList = participationRepository.findByEvent_Id(eventId);
        Long remainingSpots = event.getLimit() - participationList.size() + 1;
        response.setRemainingSpots(remainingSpots);

        List<User> attendees = participationList.stream().filter(U -> U.getParticipationType() == ParticipationType.ATTENDEE).map(Participation::getUser).collect(Collectors.toList());
        response.setAttendees(attendees);
        User host = participationList.stream().filter(U -> U.getParticipationType() == ParticipationType.HOST).map(Participation::getUser).collect(Collectors.toList()).get(0);
        response.setHost(host);

        Participation participation = participationRepository.findByUser_IdAndEvent_Id(userId, eventId);
        if (Objects.nonNull(participation))
            response.setParticipationType(participation.getParticipationType().getKey());

        return response;
    }

}
