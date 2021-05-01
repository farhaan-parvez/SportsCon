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

        Event event = new Event();
        if (StringUtils.isEmpty(request.getName()))
            throw new Exception("Event Name cannot be empty");
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
        List<Participation> participationList = participationRepository.findByEvent_Id(eventId);
        Long remainingSpots = event.getLimit() - participationList.size();
        response.setRemainingSpots(remainingSpots);

        List<User> users = participationList.stream().map(Participation::getUser).collect(Collectors.toList());
        response.setAttendees(users);

        Participation participation = participationRepository.findByUser_IdAndEvent_Id(userId, eventId);
        if (Objects.nonNull(participation))
            response.setParticipationType(participation.getParticipationType().getKey());

        return response;
    }

}
