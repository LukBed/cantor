package pl.lukbed.ecantor.users;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByMail(String mail);
    Optional<UserEntity> findByUsername(String username);
}
