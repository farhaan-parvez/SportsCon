package capstone.test.sampledep.service;

import capstone.test.sampledep.data.Event;
import capstone.test.sampledep.data.Participation;
import capstone.test.sampledep.data.User;
import capstone.test.sampledep.pojo.ParticipationType;
import capstone.test.sampledep.repository.ParticipationRepository;
import capstone.test.sampledep.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class ParticipationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ParticipationRepository participationRepository;

    public Participation createParticipationByHost(Long userId, Event event) throws Exception{
        Participation oldPart = participationRepository.findByUser_IdAndEvent_Id(userId, event.getId());
        if (Objects.nonNull(oldPart))
            throw new Exception("User cannot be host, already part of event");
        Participation participation = new Participation();
        participation.setEvent(event);
        participation.setParticipationType(ParticipationType.HOST);
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent())
            throw new Exception("No user with given id" + userId);
        participation.setUser(user.get());
        return participationRepository.save(participation);
    }

    public Participation createParticipationByAttendee(Long userId, Event event) throws Exception {
        Participation oldPart = participationRepository.findByUser_IdAndEvent_Id(userId, event.getId());
        if (Objects.nonNull(oldPart))
            throw new Exception("User cannot join, already part of event");
        Long participants = participationRepository.findByEvent_Id(event.getId()).stream().count() -1 ;
        if (Objects.equals(participants, event.getLimit()))
            throw new Exception("Attendance limit reched");
        Participation participation = new Participation();
        participation.setEvent(event);
        participation.setParticipationType(ParticipationType.ATTENDEE);
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent())
            throw new Exception("No user with given id" + userId);
        participation.setUser(user.get());
        return participationRepository.save(participation);
    }
}
