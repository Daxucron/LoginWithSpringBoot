package yellow.penguin.demo;


import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import yellow.penguin.demo.dto.request.LoginRequest;
import yellow.penguin.demo.security.jwt.TokenManager;
import yellow.penguin.demo.service.AuthService;

@SpringBootTest
public class AuthServiceTest {
	
	@Autowired
	private AuthService authService;
	@Autowired
	private TokenManager tokenManager;
	
	@Test
	public void testLogin() {
		LoginRequest request = new LoginRequest();
		request.setUserName("YellowPenguin");
		request.setPassword("potatoe");
		
		String token = authService.authenticate(request);
		
		boolean valid = tokenManager.validateToken(token);
		
		assertTrue(valid, "Token must be valid");
	}
	

}
