package capstone.test.sampledep.repository;

import capstone.test.sampledep.data.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventTypeRepository extends JpaRepository<EventType, Long> {

    public EventType findByType(String type);

}
