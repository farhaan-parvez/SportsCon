package capstone.test.sampledep.request;

public class RatingsRequest {

    private Long eventId;
    private Long socialRating;
    private Long skillRating;
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
