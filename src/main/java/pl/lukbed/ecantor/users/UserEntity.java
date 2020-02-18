package pl.lukbed.ecantor.users;

import com.google.common.collect.Sets;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
class UserEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER) private Set<UserRoleEntity> roles = Sets.newHashSet();
    @Column(unique = true) private String mail;
    @Column(unique = true) private String username;
    private String password;

    void addRole(UserRoleEntity userRole) {
        roles.add(userRole);
    }
}
