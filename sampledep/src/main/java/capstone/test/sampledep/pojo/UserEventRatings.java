package capstone.test.sampledep.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties
public class UserEventRatings {

    @JsonProperty("event_key")
    private String eventKey;
    private Double rating;
    @JsonProperty("total_ratings")
    private Long totalRatings;

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
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
