package yellow.penguin.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import yellow.penguin.demo.model.entity.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRole.UserRoleId> {
}
