package capstone.test.sampledep.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;

@Entity
@Table(name = "event_type")
@JsonIgnoreProperties
@Data
public class EventType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "event_type_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "EventType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}
