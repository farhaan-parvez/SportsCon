package capstone.test.sampledep.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class UserEventsTimeResponse {

    UserEventsResponse upcomingEvents;
    UserEventsResponse pastEvents;

    public UserEventsResponse getUpcomingEvents() {
        return upcomingEvents;
    }

    public void setUpcomingEvents(UserEventsResponse upcomingEvents) {
        this.upcomingEvents = upcomingEvents;
    }

    public UserEventsResponse getPastEvents() {
        return pastEvents;
    }

    public void setPastEvents(UserEventsResponse pastEvents) {
        this.pastEvents = pastEvents;
    }

    @Override
    public String toString() {
        return "UserEventsTimeResponse{" +
                "upcomingEvents=" + upcomingEvents +
                ", pastEvents=" + pastEvents +
                '}';
    }

}
