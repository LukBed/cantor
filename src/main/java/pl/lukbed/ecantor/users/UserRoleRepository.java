package pl.lukbed.ecantor.users;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {
    Optional<UserRoleEntity> findByName(String name);
}
