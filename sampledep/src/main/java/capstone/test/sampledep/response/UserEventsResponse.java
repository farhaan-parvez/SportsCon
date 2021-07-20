package capstone.test.sampledep.response;

import capstone.test.sampledep.data.Event;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties
public class UserEventsResponse {

    List<Event> hosted;
    List<Event> joined;

    public List<Event> getHosted() {
        return hosted;
    }

    public void setHosted(List<Event> hosted) {
        this.hosted = hosted;
    }

    public List<Event> getJoined() {
        return joined;
    }

    public void setJoined(List<Event> joined) {
        this.joined = joined;
    }

    @Override
    public String toString() {
        return "UserEventsResponse{" +
                "hosted=" + hosted +
                ", joined=" + joined +
                '}';
    }
}
