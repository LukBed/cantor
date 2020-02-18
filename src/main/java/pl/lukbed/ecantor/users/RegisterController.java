package pl.lukbed.ecantor.users;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.lukbed.ecantor.application.ActionResponse;

@Controller
class RegisterController {
    private final UserService userService;

    RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    String getRegister(Model model) {
        model.addAttribute("user", new UserEntity());
        return "register";
    }

    @PostMapping("/register")
    String postRegister(@ModelAttribute UserEntity user, Model model) {
        ActionResponse response = userService.register(user);
        if (response.isSuccessful()) {
            return "registered";
        } else {
            model.addAttribute("registerErrors", response.getErrors());
            model.addAttribute("user", new UserEntity());
            return "register";
        }
    }
}
