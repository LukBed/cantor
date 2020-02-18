package pl.lukbed.ecantor.users;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
class UserRoleEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private @Column(unique = true) String name;
    private String description;
}
