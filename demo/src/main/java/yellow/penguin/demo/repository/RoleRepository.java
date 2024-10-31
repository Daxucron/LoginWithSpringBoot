package yellow.penguin.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import yellow.penguin.demo.model.entity.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, String>{

}
