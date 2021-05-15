package capstone.test.sampledep.controller;

import capstone.test.sampledep.data.Ratings;
import capstone.test.sampledep.data.User;
import capstone.test.sampledep.request.LoginRequest;
import capstone.test.sampledep.request.RatingsRequest;
import capstone.test.sampledep.request.RegisterUserRequest;
import capstone.test.sampledep.response.UserResponse;
import capstone.test.sampledep.service.RatingsService;
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
    private UserService userService;

    @Autowired
    private RatingsService ratingsService;

    @GetMapping("/get_user_profile")
    public ResponseEntity<UserResponse> getUser(@RequestParam Long userId) {
        User user = userService.getUserById(userId);
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
    public ResponseEntity<User> loginUser(@RequestParam String email, @RequestParam String password) throws Exception{
        User user = userService.loginUser(email, password);
        return new ResponseEntity<User> (user, HttpStatus.OK);
    }

    @PostMapping("/rate_user")
    public ResponseEntity<Ratings> rateUser(@RequestParam Long userId, @RequestParam Long raterId, @RequestBody RatingsRequest ratingsRequest) throws Exception{
        Ratings ratings = ratingsService.saveRating(userId, raterId, ratingsRequest);
        return new ResponseEntity<Ratings> (ratings, HttpStatus.OK);
    }
} 
