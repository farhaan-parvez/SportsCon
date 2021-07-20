package capstone.test.sampledep.repository;

import capstone.test.sampledep.data.Ratings;
import capstone.test.sampledep.data.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingsRepository extends JpaRepository<Ratings, Long> {

    public List<Ratings> findByUser_Id(Long userId);

    public List<Ratings> findByUser_IdAndEvent_IdIn(Long userId, List<Long> eventIds);

    public Ratings findByUser_IdAndRater_IdAndEvent_Id(Long userId, Long raterId, Long eventId);
}
