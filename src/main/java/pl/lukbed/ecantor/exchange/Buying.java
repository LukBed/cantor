package pl.lukbed.ecantor.exchange;

import pl.lukbed.ecantor.application.ActionResponse;
import pl.lukbed.ecantor.cantor.CantorService;
import pl.lukbed.ecantor.wallet.WalletService;

import java.math.BigDecimal;

class Buying extends Deal {
    Buying(String code, int foreignAmount, ExchangeContext context) {
        super(code, foreignAmount, context);
    }

    ActionResponse execute() {
        String message;
        var validator = new BuyingValidator(code, foreignAmount, courses, cantorResources, userWallet);

        if (validator.validate()) {
            BigDecimal standardCurrencyAmount = validator.getStandardCurrencyAmount();
            buyValidated(code, foreignAmount, standardCurrencyAmount);
            message = "You bought " + foreignAmount + " " + code + " for " + standardCurrencyAmount + " PLN";
        } else {
            message = "Transaction failed";
        }

        return ActionResponse.ofMessage(message);
    }

    private void buyValidated(String code, int amount, BigDecimal standardCurrencyAmount) {
        BigDecimal foreignCurrencyAmount = BigDecimal.valueOf(amount);
        CantorService cantorService = context.getCantorService();
        WalletService walletService = context.getWalletService();

        cantorService.subtractCurrency(code, foreignCurrencyAmount);
        cantorService.addCurrency("PLN", standardCurrencyAmount);
        walletService.addCurrency(code, foreignCurrencyAmount);
        walletService.subtractCurrency("PLN", standardCurrencyAmount);
    }
}
