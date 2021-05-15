package capstone.test.sampledep.service;

import capstone.test.sampledep.data.Event;
import capstone.test.sampledep.data.Ratings;
import capstone.test.sampledep.data.User;
import capstone.test.sampledep.repository.EventRepository;
import capstone.test.sampledep.repository.RatingsRepository;
import capstone.test.sampledep.repository.UserRepository;
import capstone.test.sampledep.request.RatingsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RatingsService {

    @Autowired
    private RatingsRepository ratingsRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

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
        Ratings ratings = new Ratings();
        ratings.setEvent(optionalEvent.get());
        ratings.setUser(optionalUser.get());
        ratings.setRater(optionalRater.get());
        ratings.setReview(request.getReview());
        ratings.setSkillRating(request.getSkillRating());
        ratings.setSocialRating(request.getSocialRating());
        return ratingsRepository.save(ratings);
    }
}
