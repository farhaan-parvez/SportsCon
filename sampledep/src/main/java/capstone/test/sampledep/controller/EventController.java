package capstone.test.sampledep.controller;


import capstone.test.sampledep.data.Event;
import capstone.test.sampledep.data.Participation;
import capstone.test.sampledep.data.User;
import capstone.test.sampledep.request.CreateEventRequest;
import capstone.test.sampledep.request.RegisterUserRequest;
import capstone.test.sampledep.response.GetEventResponse;
import capstone.test.sampledep.response.UserEventsResponse;
import capstone.test.sampledep.response.UserEventsTimeResponse;
import capstone.test.sampledep.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping("/create_event")
    public ResponseEntity<Event> registerEvent(@RequestParam("user_id") Long userId, @RequestBody CreateEventRequest createEventRequest) throws Exception{
        Event event = eventService.createEvent(userId, createEventRequest);
        return new ResponseEntity<Event> (event, HttpStatus.OK);
    }

    @PutMapping("/edit_event")
    public ResponseEntity<Event> updateEvent(@RequestParam("user_id") Long userId, @RequestParam("event_id") Long eventId, @RequestBody CreateEventRequest createEventRequest) throws Exception{
        Event event = eventService.updateEvent(userId, eventId, createEventRequest);
        return new ResponseEntity<Event> (event, HttpStatus.OK);
    }

    @PostMapping("/join_event")
    public ResponseEntity<Participation> joinEvent(@RequestParam("user_id") Long userId, @RequestParam("event_id") Long eventId) throws Exception{
        Participation participation = eventService.joinEvent(userId, eventId);
        return new ResponseEntity<Participation> (participation, HttpStatus.OK);
    }

    @PutMapping("/leave_event")
    public ResponseEntity<Event> leaveEvent(@RequestParam("user_id") Long userId, @RequestParam("event_id") Long eventId) throws Exception{
        Event eventParticipation = eventService.leaveEvent(userId, eventId);
        return new ResponseEntity<Event> (eventParticipation, HttpStatus.OK);
    }

    @GetMapping("/get_user_events")
    public ResponseEntity<UserEventsTimeResponse> getUserEvents(@RequestParam("user_id") Long userId) throws Exception{
        UserEventsTimeResponse userEventsResponse = eventService.getUserEvents(userId);
        return new ResponseEntity<UserEventsTimeResponse>(userEventsResponse, HttpStatus.OK);
    }

    @GetMapping("/get_event")
    public ResponseEntity<GetEventResponse> getEvent(@RequestParam ("user_id") Long userId, @RequestParam("event_id") Long eventId) throws Exception{
        GetEventResponse getEventResponse = eventService.getEvent(userId, eventId);
        return new ResponseEntity<GetEventResponse>(getEventResponse, HttpStatus.OK);
    }

    @GetMapping("/search_event")
    public ResponseEntity<List<Event>> getEvent(@RequestParam (name = "event_type", required = false) String eventType, @RequestParam(name = "location", required = false) String location,
                                                     @RequestParam(name = "event_name", required = false) String eventName, @RequestParam(name = "date", required = false) Long time) throws Exception{
        List<Event> events= eventService.searchEvent(eventType, eventName, location, time);
        return new ResponseEntity<List<Event>>(events, HttpStatus.OK);
    }
}
