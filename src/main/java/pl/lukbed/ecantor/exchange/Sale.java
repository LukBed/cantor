package pl.lukbed.ecantor.exchange;

import pl.lukbed.ecantor.application.ActionResponse;
import pl.lukbed.ecantor.cantor.CantorService;
import pl.lukbed.ecantor.wallet.WalletService;

import java.math.BigDecimal;

class Sale extends Deal {
    Sale(String code, int foreignAmount, ExchangeContext context) {
        super(code, foreignAmount, context);
    }

    ActionResponse execute() {
        String message;
        var validator = new SellValidator(code, foreignAmount, courses, cantorResources, userWallet);

        if (validator.validate()) {
            BigDecimal standardCurrencyAmount = validator.getStandardCurrencyAmount();
            sellValidated(code, foreignAmount, standardCurrencyAmount);
            message = "You sell " + foreignAmount + " " + code + " for " + standardCurrencyAmount + " PLN";
        } else {
            message = "Transaction failed";
        }

        return ActionResponse.ofMessage(message);
    }

    private void sellValidated(String code, int amount, BigDecimal standardCurrencyAmount) {
        BigDecimal foreignCurrencyAmount = BigDecimal.valueOf(amount);
        CantorService cantorService = context.getCantorService();
        WalletService walletService = context.getWalletService();

        cantorService.addCurrency(code, foreignCurrencyAmount);
        cantorService.subtractCurrency("PLN", standardCurrencyAmount);
        walletService.subtractCurrency(code, foreignCurrencyAmount);
        walletService.addCurrency("PLN", standardCurrencyAmount);
    }
}
