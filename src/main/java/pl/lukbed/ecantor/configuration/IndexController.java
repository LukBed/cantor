package pl.lukbed.ecantor.configuration;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.lukbed.ecantor.users.UserService;

@Controller
class IndexController extends MainController {

    IndexController(UserService userService) {
        super(userService);
    }

    @RequestMapping("/")
    String index(Model model) {
        model.addAttribute(userService.getCurrentUser());
        return "index";
    }
}
