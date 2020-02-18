package pl.lukbed.ecantor.users;

import com.google.common.collect.Sets;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.lukbed.ecantor.application.ActionResponse;

import java.util.Set;
import java.util.regex.Pattern;

class Registerer {
    private final String defaultRoleName;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final static Pattern CORRECT_LOGIN_PATTERN = Pattern.compile("[a-zA-Z0-9_ -]*");
    private final static Pattern CORRECT_MAIL_SIGNS_PATTERN = Pattern.compile("[a-zA-Z0-9_@.-]*");

    Registerer(@Value("${ecantor.user.default-user-role}") String defaultRoleName,
               UserRepository userRepository,
               UserRoleRepository userRoleRepository,
               PasswordEncoder passwordEncoder) {
        this.defaultRoleName = defaultRoleName;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    ActionResponse register(UserEntity user) {
        Set<String> registerErrors = new Validator(user).validate();
        var registerResponse = ActionResponse.ofSet(registerErrors);
        hashPassword(user);
        if (registerResponse.isSuccessful()) {
            userRepository.save(user);
        }
        return registerResponse;
    }

    private void hashPassword(UserEntity user) {
        String passwordHash = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordHash);
    }

    private class Validator {
        private UserEntity user;
        private Set<String> registerErrors = Sets.newHashSet();

        private Validator(UserEntity user) {
            this.user = user;
        }

        Set<String> validate() {
            addDefaultRole();
            validateMail();
            validateUsername();
            validatePassword();
            return registerErrors;
        }

        private void addDefaultRole() {
            userRoleRepository.findByName(defaultRoleName)
                    .ifPresentOrElse(
                            user::addRole,
                            () -> registerErrors.add("Internal server error - default role not found"));
        }

        private void validateMail() {
            String mail = user.getMail();

            userRepository.findByMail(mail)
                    .ifPresent(u -> registerErrors.add("This mail is used by another user"));

            if (!EmailValidator.getInstance().isValid(mail) || !CORRECT_MAIL_SIGNS_PATTERN.matcher(mail).matches()) {
                registerErrors.add("Invalid mail");
            }
        }

        private void validateUsername() {
            String username = user.getUsername();

            userRepository.findByUsername(username)
                    .ifPresent(u -> registerErrors.add("This name is used by another user"));

            if (username.length() < 3 || username.length() > 20) {
                registerErrors.add("Minimum username length is 3, maximum is 20");
            }

            if (!CORRECT_LOGIN_PATTERN.matcher(username).matches()) {
                registerErrors.add("Invalid sign in username");
            }

            if (!username.equals("") &&
                    (username.charAt(0) == ' '
                    || username.charAt(username.length()-1) == ' '
                    || username.contains("  "))) {
                registerErrors.add("Invalid spaces in username");
            }
        }

        private void validatePassword() {
            if (user.getPassword().length()<3) {
                registerErrors.add("Minimum length off password is 3");
            }
        }
    }
}
