package yellow.penguin.demo.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import yellow.penguin.demo.model.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String>{

	Optional<UserEntity> findByUserName(String userName);
	Optional<UserEntity> findByEmail(String email);
}
