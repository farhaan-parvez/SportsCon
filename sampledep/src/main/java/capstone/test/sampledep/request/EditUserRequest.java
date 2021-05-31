package capstone.test.sampledep.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.NonNull;

@JsonIgnoreProperties
public class EditUserRequest {

    private String name;

    private Long phone;

    private String password;

    private String image;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "EditUserRequest{" +
                "name='" + name + '\'' +
                ", phone=" + phone +
                ", password='" + password + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
