package capstone.test.sampledep.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.NonNull;

@JsonIgnoreProperties
public class EditUserRequest {

    private String name;

    private Long phone;

    private String password;

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

    @Override
    public String toString() {
        return "EditUserRequest{" +
                "name='" + name + '\'' +
                ", phone=" + phone +
                ", password='" + password + '\'' +
                '}';
    }
}
