package capstone.test.sampledep.data;

import javax.persistence.*;

@Entity
@Table(name = "ratings")
public class Ratings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "ratings_id_seq")
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sc_user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "rater")
    private User rater;

    @ManyToOne
    @JoinColumn(name = "sc_event")
    private Event event;

    @Column(name = "skill_rating")
    private Long skillRating;

    @Column(name = "social_rating")
    private Long socialRating;

    @Column(name = "review")
    private String review;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getRater() {
        return rater;
    }

    public void setRater(User rater) {
        this.rater = rater;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Long getSkillRating() {
        return skillRating;
    }

    public void setSkillRating(Long skillRating) {
        this.skillRating = skillRating;
    }

    public Long getSocialRating() {
        return socialRating;
    }

    public void setSocialRating(Long socialRating) {
        this.socialRating = socialRating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    @Override
    public String toString() {
        return "Ratings{" +
                "id=" + id +
                ", user=" + user +
                ", rater=" + rater +
                ", event=" + event +
                ", skillRating=" + skillRating +
                ", socialRating=" + socialRating +
                ", review='" + review + '\'' +
                '}';
    }
}
