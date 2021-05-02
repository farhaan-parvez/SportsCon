package capstone.test.sampledep.data;

import capstone.test.sampledep.pojo.ParticipationType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "participation")
@JsonIgnoreProperties
@Data
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "participation_id_seq")
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sc_user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "event")
    private Event event;

    @Column(name = "participation_type")
    @Enumerated(EnumType.STRING)
    private ParticipationType participationType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public ParticipationType getParticipationType() {
        return participationType;
    }

    public void setParticipationType(ParticipationType participationType) {
        this.participationType = participationType;
    }

    @Override
    public String toString() {
        return "Participation{" +
                "id=" + id +
                ", user=" + user +
                ", event=" + event +
                ", participationType=" + participationType +
                '}';
    }
}
