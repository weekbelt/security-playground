package me.weekbelt.securityplayground.persistence.auth;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class UserRole extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public void setUser(User user) {
        if (this.user != null) {
            this.user.getUserRoles().remove(this);
        }
        this.user = user;
        user.getUserRoles().add(this);
    }

    public void setRole(Role role) {
        if (this.role != null) {
            this.role.getUserRoles().remove(this);
        }

        this.role = role;
        role.getUserRoles().add(this);
    }
}
