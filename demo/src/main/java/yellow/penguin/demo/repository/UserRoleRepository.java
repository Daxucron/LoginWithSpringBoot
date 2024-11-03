package yellow.penguin.demo.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import yellow.penguin.demo.model.entity.RoleEntity;
import yellow.penguin.demo.model.entity.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRole.UserRoleId> {
	
	Set<UserRole> findByIdUserId(String userId);
}
