package yellow.penguin.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import yellow.penguin.demo.dto.response.UserResponse;
import yellow.penguin.demo.model.entity.RoleEntity;
import yellow.penguin.demo.service.UserRoleService;
import yellow.penguin.demo.service.UserService;

@SpringBootTest
public class UserRoleServiceTest {
	
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private UserService userService;
	
	@Test
	public void getRolesTest() {
		UserResponse userResponse = userService.getUserByName("YellowPenguin");
		Set<RoleEntity> roles = userRoleService.getRoles(userResponse.getId());
		
		for(RoleEntity role: roles) {
			assertEquals("USER", role.getRoleName(), "El método get Roles devuelve Rol USER");
		}
	}
	

}
