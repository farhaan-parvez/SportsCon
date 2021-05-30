package capstone.test.sampledep.service;

import capstone.test.sampledep.data.*;
import capstone.test.sampledep.pojo.ParticipationType;
import capstone.test.sampledep.pojo.Scope;
import capstone.test.sampledep.repository.*;
import capstone.test.sampledep.request.CreateEventRequest;
import capstone.test.sampledep.response.GetEventResponse;
import capstone.test.sampledep.response.UserEventsResponse;
import capstone.test.sampledep.response.UserEventsTimeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.Part;
import javax.transaction.Transactional;
import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
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

    @Autowired
    private RatingsRepository ratingsRepository;

    @Transactional
    public Event createEvent(Long userId, CreateEventRequest request) throws Exception{
        if (Objects.isNull(userId))
            throw new Exception("userId of host cannot be null");
        if (StringUtils.isEmpty(request.getName()) || StringUtils.isEmpty(request.getLocation()) || StringUtils.isEmpty(request.getEventType()) || StringUtils.isEmpty(request.getScope()))
            throw new Exception("Event Name, Location, Event Type, Scope cannot be empty");
        Event event = new Event();
        event.setName(request.getName());
        EventType eventType = eventTypeRepository.findByType(request.getEventType());
        if (Objects.isNull(eventType))
            throw new Exception("No event type : " + request.getEventType());
        event.setEventType(eventType);
//        event.setEventType(eventTypeRepository.findByType(request.getEventType()));
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
        EventType eventType = eventTypeRepository.findByType(request.getEventType());
        if (Objects.isNull(eventType))
            throw new Exception("No event type : " + request.getEventType());
        event.setEventType(eventType);
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

    public Event leaveEvent(Long userId, Long eventId) throws Exception{
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        if (!optionalEvent.isPresent())
            throw new Exception("No such event with id : " + eventId);
        if (!optionalUser.isPresent())
            throw new Exception(("No such user with id : " + userId));
        Participation participation = participationRepository.findByUser_IdAndEvent_Id(optionalUser.get().getId(), optionalEvent.get().getId());
        Event event = participation.getEvent();
        if (event.getTime().isAfter(LocalDateTime.now())
                && Objects.equals(participation.getParticipationType(), ParticipationType.ATTENDEE)) {
            participationRepository.delete(participation);
            return optionalEvent.get();
        } else {
            throw new Exception(("Event has already finished or User is Host"));
        }
    }

    public UserEventsTimeResponse getUserEvents(Long userId) {
        List<Event> hostedEvents = participationRepository.findByUser_IdAndParticipationType(userId, ParticipationType.HOST)
                .stream().map(Participation::getEvent).collect(Collectors.toList());
        List<Event> joinedEvents = participationRepository.findByUser_IdAndParticipationType(userId, ParticipationType.ATTENDEE)
                .stream().map(Participation::getEvent).collect(Collectors.toList());
        List<Event> futureHostedEvents = hostedEvents.stream().filter(event -> event.getTime().isAfter(LocalDateTime.now())).collect(Collectors.toList());
        List<Event> pastHostedEvents = hostedEvents.stream().filter(event -> event.getTime().isBefore(LocalDateTime.now())).collect(Collectors.toList());
        List<Event> futureJoinedEvents = joinedEvents.stream().filter(event -> event.getTime().isAfter(LocalDateTime.now())).collect(Collectors.toList());
        List<Event> pastJoinedEvents = joinedEvents.stream().filter(event -> event.getTime().isBefore(LocalDateTime.now())).collect(Collectors.toList());
        UserEventsResponse upcomingEvents = new UserEventsResponse();
        upcomingEvents.setHosted(futureHostedEvents);
        upcomingEvents.setJoined(futureJoinedEvents);
        UserEventsResponse pastEvents = new UserEventsResponse();
        pastEvents.setHosted(pastHostedEvents);
        pastEvents.setJoined(pastJoinedEvents);
        UserEventsTimeResponse userEventsTimeResponse = new UserEventsTimeResponse();
        userEventsTimeResponse.setUpcomingEvents(upcomingEvents);
        userEventsTimeResponse.setPastEvents(pastEvents);
        return userEventsTimeResponse;
    }

    public GetEventResponse getEvent(Long userId, Long eventId) throws Exception{
        GetEventResponse response = new GetEventResponse();
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        if (!optionalEvent.isPresent())
            throw new Exception("No event with id : " + eventId);

        Event event = optionalEvent.get();
        response.setId(event.getId());
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
        //response.setAttendees(attendees);
        setRatings(userId, eventId, attendees, response);
        User host = participationList.stream().filter(U -> U.getParticipationType() == ParticipationType.HOST).map(Participation::getUser).collect(Collectors.toList()).get(0);
        response.setHost(host);

        Participation participation = participationRepository.findByUser_IdAndEvent_Id(userId, eventId);
        if (Objects.nonNull(participation))
            response.setParticipationType(participation.getParticipationType().getKey());

        return response;
    }

    private void setRatings(Long userId, Long eventId, List<User> attendees, GetEventResponse response) {
        for (User attendee : attendees) {
            attendee.setRated(false);
            Ratings ratings = ratingsRepository.findByUser_IdAndRater_IdAndEvent_Id(attendee.getId(), userId, eventId);
            if (Objects.nonNull(ratings)) {
                attendee.setRated(true);
                attendee.setReview(ratings.getReview());
                attendee.setSkillRating(ratings.getSkillRating());
                attendee.setSocialRating(ratings.getSocialRating());
            }
        }
        response.setAttendees(attendees);
    }

    public List<Event> searchEvent(String eventType, String eventName, String location, Long time) throws Exception{
        List<Event> events = eventRepository.findAll().stream()
                .filter(e -> Objects.equals(e.getScope(), Scope.PUBLIC))
                .filter(e -> e.getTime().isAfter(LocalDateTime.now()))
                .collect(Collectors.toList());
        if (!StringUtils.isEmpty(eventType)) {
            events = events.stream()
                    .filter(e -> e.getEventType().getType().toLowerCase().contains(eventType.toLowerCase())).collect(Collectors.toList());
        }
        if (!StringUtils.isEmpty(eventName)) {
            events = events.stream()
                    .filter(e -> e.getName().toLowerCase().contains(eventName.toLowerCase())).collect(Collectors.toList());
        }
        if (!StringUtils.isEmpty(location)) {
            events = events.stream()
                    .filter(e -> e.getLocation().toLowerCase().contains(location.toLowerCase())).collect(Collectors.toList());
        }
        if (!StringUtils.isEmpty(time)) {
            LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
            events = events.stream()
                    .filter(e -> e.getTime().toLocalDate().isEqual(localDateTime.toLocalDate())).collect(Collectors.toList());
        }
        return events;
    }

}
