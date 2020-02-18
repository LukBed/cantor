package pl.lukbed.ecantor.exchange;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.lukbed.ecantor.application.ActionResponse;
import pl.lukbed.ecantor.configuration.MainController;
import pl.lukbed.ecantor.users.UserService;
import pl.lukbed.ecantor.wallet.WalletService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.function.BiFunction;

@Controller
class ExchangeController extends MainController {
    private final ExchangeService exchangeService;
    private final WalletService walletService;

    ExchangeController(UserService userService,
                       ExchangeService exchangeService,
                       WalletService walletService) {
        super(userService);
        this.exchangeService = exchangeService;
        this.walletService = walletService;
    }

    @GetMapping("/exchange")
    String exchange(Model model) {
        addExchangeDtoToModel(model);
        return "exchange";
    }

    @GetMapping("/credit")
    String credit(Model model) {
        walletService.addCurrency("PLN", BigDecimal.valueOf(1000));
        addMessageToModel("You got 1 000 PLN of credit", model);
        addExchangeDtoToModel(model);
        return "exchange";
    }

    @PostMapping("/buy")
    String buy(Model model, HttpServletRequest request) {
        BiFunction<String, Integer, ActionResponse> action = exchangeService::buy;
        return transact(model, request, action);
    }

    @PostMapping("/sell")
    String sell(Model model, HttpServletRequest request) {
        BiFunction<String, Integer, ActionResponse> action = exchangeService::sell;
        return transact(model, request, action);
    }

    private String transact(Model model, HttpServletRequest request,
                            BiFunction<String, Integer, ActionResponse> action) {
        String currency = request.getParameter("currency");
        String amount = request.getParameter("amount");
        try {
            int intAmount = Integer.parseInt(amount);
            ActionResponse response = action.apply(currency, intAmount);
            if (!response.isSuccessful()) {
                String message = response.getErrors().iterator().next();
                addMessageToModel(message, model);
            }
        } catch (NumberFormatException exception) {
            addMessageToModel(exception.getMessage(), model);
        }

        addExchangeDtoToModel(model);
        return "exchange";
    }

    private void addExchangeDtoToModel(Model model) {
        model.addAttribute("exchange", exchangeService.getExchange());
    }

    private void addMessageToModel(String message, Model model) {
        model.addAttribute("message", message);
    }
}
