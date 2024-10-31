package yellow.penguin.demo.service;

import java.util.Optional;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import yellow.penguin.demo.dto.request.CreateUserRequest;
import yellow.penguin.demo.dto.request.LoginRequest;
import yellow.penguin.demo.dto.response.UserResponse;
import yellow.penguin.demo.exception.MissingUserArgumentsForCreationRuntimeException;
import yellow.penguin.demo.model.entity.UserEntity;
import yellow.penguin.demo.model.enums.Roles;
import yellow.penguin.demo.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private UserRepository userRepository;
	
	public UserResponse createUser(CreateUserRequest userRequest){
		UserEntity user = new UserEntity(userRequest);
		user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
		if(validateUser(user)) {
			UserEntity createdUser = userRepository.save(user);
			userRoleService.createRelation(createdUser, roleService.getRoleByName(Roles.USER));
			return new UserResponse(createdUser);
		}
		return null;		
	}
	
	public boolean loginUser(LoginRequest request) {
		
		UserEntity userToFind = new UserEntity();
		userToFind.setUserName(request.getUserName());
		Optional<UserEntity> userOp = userRepository.findOne(Example.of(userToFind));
		if(userOp.isPresent()) {
			if(passwordEncoder.matches(request.getPassword(), userOp.get().getPassword())) {
				return true;
			}
		}		
		return false;
	}

	private boolean validateUser(UserEntity user){
		if(user.getPassword() != null && user.getUserName() != null && user.getEmail() != null) {
			return true;
		}
		else {
			throw new MissingUserArgumentsForCreationRuntimeException("User not valid");
		}
	}
	
}
