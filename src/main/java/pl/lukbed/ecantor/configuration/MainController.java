package pl.lukbed.ecantor.configuration;

import org.springframework.web.bind.annotation.ModelAttribute;
import pl.lukbed.ecantor.users.UserDto;
import pl.lukbed.ecantor.users.UserService;

public class MainController {
    protected final UserService userService;

    public MainController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("loggedUser")
    UserDto loggedUser() {
        return userService.getCurrentUser().orElse(null);
    }
}
