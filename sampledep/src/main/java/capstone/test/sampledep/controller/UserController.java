package capstone.test.sampledep.controller;

import capstone.test.sampledep.data.User;
import capstone.test.sampledep.request.LoginRequest;
import capstone.test.sampledep.request.RegisterUserRequest;
import capstone.test.sampledep.response.UserResponse;
import capstone.test.sampledep.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/get_user")
    public ResponseEntity<UserResponse> getUser(@RequestParam Long id) {
        User user = userService.getUserById(id);
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        return new ResponseEntity<UserResponse> (userResponse, HttpStatus.OK);
    }

    @PostMapping("/register_user")
    public ResponseEntity<User> registerUser(@RequestBody RegisterUserRequest registerUserRequest) throws Exception{
        User user = userService.registerUser(registerUserRequest);
        return new ResponseEntity<User> (user, HttpStatus.OK);
    }

    @GetMapping("/login_user")
    public ResponseEntity<User> loginUser(@RequestBody LoginRequest loginRequest) throws Exception{
        User user = userService.loginUser(loginRequest);
        return new ResponseEntity<User> (user, HttpStatus.OK);
    }
}
