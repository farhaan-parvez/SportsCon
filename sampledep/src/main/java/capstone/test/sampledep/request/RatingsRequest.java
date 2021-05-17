package capstone.test.sampledep.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RatingsRequest {

    @JsonProperty("event_id")
    private Long eventId;
    @JsonProperty("social_rating")
    private Long socialRating;
    @JsonProperty("skill_rating")
    private Long skillRating;
    @JsonProperty("review")
    private String review;

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getSocialRating() {
        return socialRating;
    }

    public void setSocialRating(Long socialRating) {
        this.socialRating = socialRating;
    }

    public Long getSkillRating() {
        return skillRating;
    }

    public void setSkillRating(Long skillRating) {
        this.skillRating = skillRating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    @Override
    public String toString() {
        return "RatingsRequest{" +
                "eventId=" + eventId +
                ", socialRating=" + socialRating +
                ", skillRating=" + skillRating +
                ", review='" + review + '\'' +
                '}';
    }
}
