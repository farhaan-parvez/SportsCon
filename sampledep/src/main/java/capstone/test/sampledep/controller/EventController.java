package capstone.test.sampledep.controller;


import capstone.test.sampledep.data.Event;
import capstone.test.sampledep.data.Participation;
import capstone.test.sampledep.data.User;
import capstone.test.sampledep.request.CreateEventRequest;
import capstone.test.sampledep.request.RegisterUserRequest;
import capstone.test.sampledep.response.GetEventResponse;
import capstone.test.sampledep.response.UserEventsResponse;
import capstone.test.sampledep.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping("/create_event/{user_id}")
    public ResponseEntity<Event> registerEvent(@PathVariable("user_id") Long userId, @RequestBody CreateEventRequest createEventRequest) throws Exception{
        Event event = eventService.createEvent(userId, createEventRequest);
        return new ResponseEntity<Event> (event, HttpStatus.OK);
    }

    @PostMapping("/join_event")
    public ResponseEntity<Participation> joinEvent(@Param("user_id") Long userId, @Param("event_id") Long eventId) throws Exception{
        Participation participation = eventService.joinEvent(userId, eventId);
        return new ResponseEntity<Participation> (participation, HttpStatus.OK);
    }

    @GetMapping("/get_user_events")
    public ResponseEntity<UserEventsResponse> getUserEvents(@Param("user_id") Long userId) throws Exception{
        UserEventsResponse userEventsResponse = eventService.getUserEvents(userId);
        return new ResponseEntity<UserEventsResponse>(userEventsResponse, HttpStatus.OK);
    }

    @GetMapping("/get_event")
    public ResponseEntity<GetEventResponse> getEvent(@Param("user_id") Long userId, @Param("event_id") Long eventId) throws Exception{
        GetEventResponse getEventResponse = eventService.getEvent(userId, eventId);
        return new ResponseEntity<GetEventResponse>(getEventResponse, HttpStatus.OK);
    }
}
