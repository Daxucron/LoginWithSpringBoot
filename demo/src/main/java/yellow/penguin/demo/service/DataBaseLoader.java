package yellow.penguin.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.transaction.Transactional;
import yellow.penguin.demo.dto.request.CreateRoleRequest;
import yellow.penguin.demo.dto.request.CreateUserRequest;
import yellow.penguin.demo.model.enums.Roles;

@Configuration
public class DataBaseLoader {

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;

	@Bean
	@Transactional
	CommandLineRunner initData() {
		return args -> {
			
		CreateRoleRequest roleRequest = new CreateRoleRequest();
		roleRequest.setRoleName(Roles.USER.name());
		roleService.createRole(roleRequest);
			
		CreateUserRequest userA = new CreateUserRequest();
		userA.setEmail("cazuso4@gmail.com");
		userA.setUserName("YellowPenguin");
		userA.setPassword("potatoe");
		userService.createUser(userA);
		};

	}

}
