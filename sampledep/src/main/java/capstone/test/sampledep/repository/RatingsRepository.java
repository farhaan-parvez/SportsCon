package capstone.test.sampledep.repository;

import capstone.test.sampledep.data.Ratings;
import capstone.test.sampledep.data.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingsRepository extends JpaRepository<Ratings, Long> {

    public Ratings findByUser_Id(long userId);

}
