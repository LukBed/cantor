package pl.lukbed.ecantor.users;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
class UserConfiguration {
    private final String defaultRoleName;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    UserConfiguration(@Value("${ecantor.user.default-user-role}") String defaultRoleName,
                      UserRepository userRepository,
                      UserRoleRepository userRoleRepository,
                      PasswordEncoder passwordEncoder) {
        this.defaultRoleName = defaultRoleName;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void addDefaultUserRole() {
        if (userRoleRepository.findByName(defaultRoleName).isEmpty()) {
            UserRoleEntity defaultRole = new UserRoleEntity();
            defaultRole.setName(defaultRoleName);
            defaultRole.setDescription("standard user");
            userRoleRepository.save(defaultRole);
        }
    }

    @Bean
    UserService userService() {
        UserFactory factory = new UserFactory();
        return factory.createService(defaultRoleName, userRepository, userRoleRepository, passwordEncoder);
    }
}
