package yellow.penguin.demo.model.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "user_role")
@Data
public class UserRole {

    @EmbeddedId
    private UserRoleId id;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Embeddable
    @Data
    public static class UserRoleId implements java.io.Serializable {

		private static final long serialVersionUID = 1L;

		@Column(name = "user_id", length = 36)
        private String userId;

        @Column(name = "role_id", length = 36)
        private String roleId;
    }
}