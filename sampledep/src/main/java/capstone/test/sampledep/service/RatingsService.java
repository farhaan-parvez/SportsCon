package capstone.test.sampledep.service;

import capstone.test.sampledep.data.*;
import capstone.test.sampledep.pojo.UserEventRatings;
import capstone.test.sampledep.repository.*;
import capstone.test.sampledep.request.RatingsRequest;
import capstone.test.sampledep.response.UserProfileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RatingsService {

    @Autowired
    private RatingsRepository ratingsRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventTypeRepository eventTypeRepository;

    @Autowired
    private ParticipationRepository participationRepository;


    public Ratings saveRating(Long userId, Long raterId, RatingsRequest request) throws Exception{
        Optional<Event> optionalEvent = eventRepository.findById(request.getEventId());
        if (!optionalEvent.isPresent())
            throw new Exception("No such event with id : " + request.getEventId());
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent())
            throw new Exception("No such user with id : " + userId);
        Optional<User> optionalRater = userRepository.findById(raterId);
        if (!optionalRater.isPresent())
            throw new Exception("No such user with id : " + raterId);
        Participation userParticipation = participationRepository.findByUser_IdAndEvent_Id(userId, request.getEventId());
        if (Objects.isNull(userParticipation))
            throw new Exception("User did not participate in this event " + userId + " " + request.getEventId());
        Participation raterParticipation = participationRepository.findByUser_IdAndEvent_Id(raterId, request.getEventId());
        if (Objects.isNull(raterParticipation))
            throw new Exception("Rater did not participate in this event " + raterId + " " + request.getEventId());
        Ratings prevRatings = ratingsRepository.findByUser_IdAndRater_IdAndEvent_Id(userId, raterId, request.getEventId());
//        if(Objects.nonNull(prevRatings))
//            throw new Exception("User already rated by this rater for this event");
        Ratings ratings = new Ratings();
        if (Objects.nonNull(prevRatings))
            ratings = prevRatings;
        ratings.setEvent(optionalEvent.get());
        ratings.setUser(optionalUser.get());
        ratings.setRater(optionalRater.get());
        ratings.setReview(request.getReview());
        ratings.setSkillRating(request.getSkillRating());
        ratings.setSocialRating(request.getSocialRating());
        return ratingsRepository.save(ratings);
    }

    public void setUserProfileResponse(Long userId, UserProfileResponse userProfileResponse) {
        List<Ratings> ratings = ratingsRepository.findByUser_Id(userId);
        List<String> reviews = ratings.stream().filter(el -> Objects.nonNull(el)).map(Ratings::getReview).collect(Collectors.toList());
        userProfileResponse.setReviews(reviews);
        Double socialRating = ratings.stream().filter(el -> Objects.nonNull(el.getSocialRating())).mapToDouble(Ratings::getSocialRating).average().orElse(Double.NaN);
        userProfileResponse.setSocialRating(socialRating);
        userProfileResponse.setTotalSocial(ratings.stream().filter(el -> Objects.nonNull(el.getSocialRating())).count());
        List<EventType> eventTypes = eventTypeRepository.findAll();
        List<Event> events = participationRepository.findByUser_Id(userId).stream().map(Participation::getEvent).collect(Collectors.toList());
        List<UserEventRatings> userEventRatingsList = new ArrayList<>();
        for (EventType eventType : eventTypes) {
            UserEventRatings userEventRatings = new UserEventRatings();
            userEventRatings.setEventKey(eventType.getType());
            List<Long> eventIds = events.stream().filter(event -> event.getEventType() == eventType).map(Event::getId).collect(Collectors.toList());
            List<Ratings> eventRatings = ratingsRepository.findByUser_IdAndEvent_IdIn(userId, eventIds);
            userEventRatings.setTotalRatings(Long.valueOf(eventRatings.size()));
            Double eventRating = eventRatings.stream().filter(el -> Objects.nonNull(el.getSkillRating())).mapToDouble(Ratings::getSkillRating).average().orElse(Double.NaN);
            userEventRatings.setRating(eventRating);
            userEventRatingsList.add(userEventRatings);
        }
        userProfileResponse.setEventRatings(userEventRatingsList);
    }
}
