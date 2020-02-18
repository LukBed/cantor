package pl.lukbed.ecantor.users;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import pl.lukbed.ecantor.application.ActionResponse;

import java.util.Optional;

public class UserService {
    private final Registerer registerer;
    private final UserRepository userRepository;

    UserService(Registerer registerer,
                UserRepository userRepository) {
        this.registerer = registerer;
        this.userRepository = userRepository;
    }

    ActionResponse register(UserEntity user) {
        return registerer.register(user);
    }

    public Optional<UserDto> getCurrentUser() {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String mail;

        if (principal instanceof UserDetails) {
            mail = ((UserDetails)principal).getUsername();
        } else {
            mail = principal.toString();
        }

        return userRepository.findByMail(mail).map(UserDto::fromEntity);
    }
}
