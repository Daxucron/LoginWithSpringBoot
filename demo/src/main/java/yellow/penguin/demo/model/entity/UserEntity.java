package yellow.penguin.demo.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import yellow.penguin.demo.dto.request.CreateUserRequest;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class UserEntity {
	
	public UserEntity(CreateUserRequest createUserRequest) {
		this.userName = createUserRequest.getUserName();
		this.email = createUserRequest.getEmail();
	}

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	@Column(name = "username")
	private String userName;
	private String password;
	private String email;

}
