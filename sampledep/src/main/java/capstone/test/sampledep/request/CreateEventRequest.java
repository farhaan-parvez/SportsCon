package capstone.test.sampledep.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties
public class CreateEventRequest {

    private String name;
    @JsonProperty("event_type")
    private String eventType;
    private String description;
    private String location;
    private Long time;
    private Long limit;
    private String scope;

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

    @Override
    public String toString() {
        return "CreateEventRequest{" +
                "name='" + name + '\'' +
                ", eventType='" + eventType + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", time=" + time +
                ", limit=" + limit +
                ", scope='" + scope + '\'' +
                '}';
    }
}
