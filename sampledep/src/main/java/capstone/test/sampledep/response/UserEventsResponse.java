package capstone.test.sampledep.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties
public class UserEventsResponse {

    List<Long> hosted;
    List<Long> joined;

    public List<Long> getHosted() {
        return hosted;
    }

    public void setHosted(List<Long> hosted) {
        this.hosted = hosted;
    }

    public List<Long> getJoined() {
        return joined;
    }

    public void setJoined(List<Long> joined) {
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
