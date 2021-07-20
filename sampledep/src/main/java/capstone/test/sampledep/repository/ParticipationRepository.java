package capstone.test.sampledep.repository;

import capstone.test.sampledep.data.Participation;
import capstone.test.sampledep.pojo.ParticipationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipationRepository extends JpaRepository<Participation, Long> {

    List<Participation> findByUser_IdAndParticipationType(Long userId, ParticipationType participationType);

    List<Participation> findByEvent_Id(Long eventId);

    List<Participation> findByUser_Id(Long userId);

    Participation findByUser_IdAndEvent_Id(Long userId, Long eventId);
}
