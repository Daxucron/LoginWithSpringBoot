package yellow.penguin.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import yellow.penguin.demo.model.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String>{

}
