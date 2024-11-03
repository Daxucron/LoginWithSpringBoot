package yellow.penguin.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import yellow.penguin.demo.model.entity.UserEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
	
	private String id;
	private String userName;
	private String email;

	public UserResponse(UserEntity createdUser) {
		this.id = createdUser.getId();
		this.userName = createdUser.getUserName();
		this.email = createdUser.getEmail();		
	}

}
