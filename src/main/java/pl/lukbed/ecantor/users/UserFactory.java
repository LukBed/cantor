package pl.lukbed.ecantor.users;

import org.springframework.security.crypto.password.PasswordEncoder;

class UserFactory {
    UserService createService(String defaultRoleName,
                              UserRepository userRepository,
                              UserRoleRepository userRoleRepository,
                              PasswordEncoder passwordEncoder) {

        Registerer registerer = new Registerer(defaultRoleName, userRepository, userRoleRepository, passwordEncoder);
        return new UserService(registerer, userRepository);
    }
}
