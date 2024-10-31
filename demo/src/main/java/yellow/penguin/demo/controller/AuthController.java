package yellow.penguin.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import yellow.penguin.demo.dto.request.CreateUserRequest;
import yellow.penguin.demo.dto.request.LoginRequest;
import yellow.penguin.demo.dto.response.UserResponse;
import yellow.penguin.demo.service.UserService;

@RestController
public class AuthController {

	@Autowired
    private UserService userService;
    
    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody CreateUserRequest userRequest) {
        try {
            UserResponse userResponse = userService.createUser(userRequest);
            return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    
    //TODO: que devuelva el token
    @PostMapping("/login")
    public String loginUser(@RequestBody LoginRequest request) {
        if(userService.loginUser(request)) {            
            return "Usuario logeado";
        }else {
            return "NOPE";
        }
    }
    
    @GetMapping("/test")
    public String testEndpoint() {
        return "Hola mundo";
    }
    
}
