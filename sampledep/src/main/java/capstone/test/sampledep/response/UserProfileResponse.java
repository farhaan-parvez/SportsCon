package capstone.test.sampledep.response;

import capstone.test.sampledep.data.User;
import capstone.test.sampledep.pojo.UserEventRatings;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@JsonIgnoreProperties
public class UserProfileResponse {

    private User accountDetails;

    private Long socialRating;

    private List<UserEventRatings> eventRatings;

    private List<String> reviews;
}
