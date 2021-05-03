package capstone.test.sampledep.response;

import capstone.test.sampledep.data.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties
public class GetEventResponse {
    private Long id;
    private String name;
    @JsonProperty("event_type")
    private String eventType;
    private String description;
    private String location;
    private Long time;
    private Long limit;
    private String scope;
    @JsonProperty("remainining_spots")
    private Long remainingSpots;
    private User host;
    private List<User> attendees;
    @JsonProperty("participation_type")
    private String participationType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getLimit() {
        return limit;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Long getRemainingSpots() {
        return remainingSpots;
    }

    public void setRemainingSpots(Long remainingSpots) {
        this.remainingSpots = remainingSpots;
    }

    public List<User> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<User> attendees) {
        this.attendees = attendees;
    }

    public String getParticipationType() {
        return participationType;
    }

    public void setParticipationType(String participationType) {
        this.participationType = participationType;
    }

    public User getHost() {
        return host;
    }

    public void setHost(User host) {
        this.host = host;
    }

    @Override
    public String toString() {
        return "GetEventResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", eventType='" + eventType + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", time=" + time +
                ", limit=" + limit +
                ", scope='" + scope + '\'' +
                ", remainingSpots=" + remainingSpots +
                ", host=" + host +
                ", attendees=" + attendees +
                ", participationType='" + participationType + '\'' +
                '}';
    }
}
