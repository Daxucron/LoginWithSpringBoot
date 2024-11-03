package yellow.penguin.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yellow.penguin.demo.dto.request.LoginRequest;
import yellow.penguin.demo.dto.response.UserResponse;
import yellow.penguin.demo.security.jwt.TokenManager;

@Service
public class AuthService {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TokenManager tokenManager;
	
	public String authenticate(LoginRequest loginRequest) {		
		try {
			UserResponse userResponse = userService.loginUser(loginRequest);
			return tokenManager.generateToken(userResponse.getId());
			
		} catch (Exception e) {
			System.out.println(e);
			throw new RuntimeException("Credenciales inválidas");
		}       
    }

}
