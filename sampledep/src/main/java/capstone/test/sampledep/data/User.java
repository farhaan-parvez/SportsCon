package capstone.test.sampledep.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "sc_user")
//JsonIgnoreProperties("password")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "sc_user_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private Long phone;

    @NonNull
    @Column(name = "email")
    private String email;

    @Column(name = "age")
    private Integer age;

    @Column(name = "password")
    private String password;

    @Column(name = "image")
    private String image;

    @Transient
    private Boolean rated = false;
    
    @Transient
    private Long skillRating;

    @Transient
    private Long socialRating;

    @Transient
    private String review;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getRated() {
        return rated;
    }

    public void setRated(Boolean rated) {
        this.rated = rated;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone=" + phone +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", password='" + password + '\'' +
                ", image='" + image + '\'' +
                ", rated=" + rated +
                ", skillRating=" + skillRating +
                ", socialRating=" + socialRating +
                ", review='" + review + '\'' +
                '}';
    }
}
