package yellow.penguin.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import yellow.penguin.demo.dto.request.CreateRoleRequest;
import yellow.penguin.demo.model.entity.RoleEntity;
import yellow.penguin.demo.model.enums.Roles;
import yellow.penguin.demo.repository.RoleRepository;

@Service
public class RoleService {
	
	@Autowired
	RoleRepository roleRepository;
	
	public RoleEntity createRole(CreateRoleRequest request) {
		return roleRepository.save(new RoleEntity(request));		
	}
	
	public RoleEntity getRoleByName(Roles role) {
		RoleEntity roleEntity = new RoleEntity();
		roleEntity.setRoleName(role.name());
		Optional<RoleEntity> dbResponse = roleRepository.findOne(Example.of(roleEntity));
		if(dbResponse.isPresent()) {
			return dbResponse.get();
		}
		return null;
	}
	
	
	

}
