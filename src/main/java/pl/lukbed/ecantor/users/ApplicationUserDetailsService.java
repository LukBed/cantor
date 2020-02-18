package pl.lukbed.ecantor.users;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
class ApplicationUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    ApplicationUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        UserEntity user = userRepository
                .findByMail(mail)
                .orElseThrow(() -> new UsernameNotFoundException("User with mail not found"));
        return new User(user.getMail(), user.getPassword(), convertAuthorities(user.getRoles()));
    }

    private Set<GrantedAuthority> convertAuthorities(Set<UserRoleEntity> userRoles) {
        return userRoles.stream()
                .map((role) -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());
    }
}
