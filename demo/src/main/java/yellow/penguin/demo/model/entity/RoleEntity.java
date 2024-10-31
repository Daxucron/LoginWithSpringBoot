package yellow.penguin.demo.model.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import yellow.penguin.demo.dto.request.CreateRoleRequest;

@Entity
@Table(name = "role")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "role_id", length = 36)
    private String roleId;

    @Column(name = "role_name", length = 20, unique = true)
    private String roleName;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;
    
    public RoleEntity (CreateRoleRequest request) {    	
    	this.roleName = request.getRoleName();
    	this.createdAt = new Timestamp(System.currentTimeMillis());
    	this.updatedAt = this.createdAt;
    }
}