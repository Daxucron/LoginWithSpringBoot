package yellow.penguin.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import yellow.penguin.demo.dto.request.CreateUserRequest;
import yellow.penguin.demo.dto.request.LoginRequest;
import yellow.penguin.demo.dto.response.UserResponse;
import yellow.penguin.demo.dto.response.UserResponseError;
import yellow.penguin.demo.service.AuthService;
import yellow.penguin.demo.service.UserService;

@RestController
public class AuthController {
	
	@Autowired
	private AuthService authService;

	@Autowired
    private UserService userService;
    
	//TODO: Cambiar el register por la interfaz del authService y eliminar el userService de este controller
    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody CreateUserRequest userRequest) {
    	UserResponse userResponse;
        try {
        	userResponse = userService.createUser(userRequest);
            return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
        } catch (Exception e) {
        	userResponse = new UserResponseError(e.getMessage());
            return new ResponseEntity<>(userResponse, HttpStatus.BAD_REQUEST);
        }
    }
    

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request) {    	
    	 try {
             String token = authService.authenticate(request);
             HttpHeaders headers = new HttpHeaders();
             headers.set("Authorization", "Bearer " + token);
             return ResponseEntity.ok()
                     .headers(headers)
                     .body("Login success");

         } catch (RuntimeException e) {
             return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong username or password");
         }
    }
    
    @GetMapping("/test")
    public String testEndpoint() {
        return "Hola mundo";
    }
    
}
