package yellow.penguin.demo.service;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yellow.penguin.demo.model.entity.RoleEntity;
import yellow.penguin.demo.model.entity.UserEntity;
import yellow.penguin.demo.model.entity.UserRole;
import yellow.penguin.demo.repository.UserRoleRepository;

@Service
public class UserRoleService {
	
	@Autowired
	private UserRoleRepository userRoleRepository;
	
	public boolean createRelation(UserEntity userEntity, RoleEntity roleEntity) {
		try {
			UserRole.UserRoleId userRoleId = new UserRole.UserRoleId();
			userRoleId.setRoleId(roleEntity.getRoleId());
			userRoleId.setUserId(userEntity.getId());
			UserRole userRole = new UserRole();
			userRole.setId(userRoleId);
			userRole.setCreatedAt(new Timestamp(System.currentTimeMillis()));
			userRoleRepository.save(userRole);
			return true;
		}catch (Exception e) {
			return false;
		}
	}

}
