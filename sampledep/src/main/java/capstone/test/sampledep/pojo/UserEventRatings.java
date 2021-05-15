package capstone.test.sampledep.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class UserEventRatings {

    private String eventKey;
    private Long rating;
    private Long totalRatings;

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }

    public Long getTotalRatings() {
        return totalRatings;
    }

    public void setTotalRatings(Long totalRatings) {
        this.totalRatings = totalRatings;
    }

    @Override
    public String toString() {
        return "UserEventRatings{" +
                "eventKey='" + eventKey + '\'' +
                ", rating=" + rating +
                ", totalRatings=" + totalRatings +
                '}';
    }
}
