package capstone.test.sampledep.response;

import capstone.test.sampledep.data.User;
import capstone.test.sampledep.pojo.UserEventRatings;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@JsonIgnoreProperties
public class UserProfileResponse {

    @JsonProperty("account_details")
    private User accountDetails;

    @JsonProperty("social_rating")
    private Double socialRating;

    @JsonProperty("total_social_ratings")
    private Long totalSocial;

    @JsonProperty("event_ratings")
    private List<UserEventRatings> eventRatings;

    private List<String> reviews;

    public User getAccountDetails() {
        return accountDetails;
    }

    public void setAccountDetails(User accountDetails) {
        this.accountDetails = accountDetails;
    }

    public Double getSocialRating() {
        return socialRating;
    }

    public void setSocialRating(Double socialRating) {
        this.socialRating = socialRating;
    }

    public List<UserEventRatings> getEventRatings() {
        return eventRatings;
    }

    public void setEventRatings(List<UserEventRatings> eventRatings) {
        this.eventRatings = eventRatings;
    }

    public List<String> getReviews() {
        return reviews;
    }

    public void setReviews(List<String> reviews) {
        this.reviews = reviews;
    }

    public Long getTotalSocial() {
        return totalSocial;
    }

    public void setTotalSocial(Long totalSocial) {
        this.totalSocial = totalSocial;
    }

    @Override
    public String toString() {
        return "UserProfileResponse{" +
                "accountDetails=" + accountDetails +
                ", socialRating=" + socialRating +
                ", totalSocial=" + totalSocial +
                ", eventRatings=" + eventRatings +
                ", reviews=" + reviews +
                '}';
    }
}
