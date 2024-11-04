package yellow.penguin.demo.service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import yellow.penguin.demo.dto.request.CreateUserRequest;
import yellow.penguin.demo.dto.request.LoginRequest;
import yellow.penguin.demo.dto.response.UserResponse;
import yellow.penguin.demo.exception.MissingUserArgumentsForCreationRuntimeException;
import yellow.penguin.demo.exception.UserAlreadyExistsException;
import yellow.penguin.demo.model.entity.RoleEntity;
import yellow.penguin.demo.model.entity.UserEntity;
import yellow.penguin.demo.model.enums.Roles;
import yellow.penguin.demo.repository.UserRepository;

@Service
public class UserService implements UserDetailsService{
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private UserRepository userRepository;
	
	public UserResponse createUser(CreateUserRequest userRequest) throws Exception{
		UserEntity user = new UserEntity(userRequest);
		user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
		if(validateUser(user)) {
			if(userExists(user)) {
				throw new UserAlreadyExistsException("Username or email duplicated");
			}
			UserEntity createdUser = userRepository.save(user);
			userRoleService.createRelation(createdUser, roleService.getRoleByName(Roles.USER));
			return new UserResponse(createdUser);				
		}
		return null;		
	}
	
	private boolean userExists(UserEntity user) {
		Optional<UserEntity> userOp  = userRepository.findByUserName(user.getUserName());
		if(userOp.isPresent()) {
			return true;
		}
		userOp = userRepository.findByEmail(user.getEmail());
		if(userOp.isPresent()) {
			return true;
		}
		return false;
	}

	public UserResponse loginUser(LoginRequest request) throws Exception {		
		UserEntity userToFind = new UserEntity();
		userToFind.setUserName(request.getUserName());
		Optional<UserEntity> userOp = userRepository.findOne(Example.of(userToFind));
		if(userOp.isPresent()) {
			if(passwordEncoder.matches(request.getPassword(), userOp.get().getPassword())) {
				return new UserResponse(userOp.get());
			}
		}		
		throw new Exception("Wrong Password");
	}
	
	public UserResponse getUserByUserName(String userName) {
		Optional<UserEntity> userOp = userRepository.findByUserName(userName);
		if(userOp.isPresent()) {
			UserEntity userEntity = userOp.get();
			return new UserResponse(userEntity);
		}
		throw new UsernameNotFoundException("User not found with username = " + userName);		
	}
	
	private UserEntity getUserById(String userId) throws UsernameNotFoundException {
		Optional<UserEntity> userOp =userRepository.findById(userId);
		if(userOp.isPresent()) {
			return userOp.get();
		}
		throw new UsernameNotFoundException("User not found");		
	}

	private boolean validateUser(UserEntity user){
		if(user.getPassword() != null && user.getUserName() != null && user.getEmail() != null) {
			return true;
		}
		else {
			throw new MissingUserArgumentsForCreationRuntimeException("User not valid");
		}
	}
	
	private Set<RoleEntity> getRoles(String userId){		
		return userRoleService.getRoles(userId);
	}

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		UserEntity user = getUserById(userId);
		Collection<? extends GrantedAuthority> authorities = getRoles(userId).stream()
				.map(role-> new SimpleGrantedAuthority("ROLE_".concat(role.getRoleName())))
				.collect(Collectors.toSet());
		return new User(user.getId(), user.getPassword(), true, true, true, true, authorities);
	}
	
}
