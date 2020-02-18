package pl.lukbed.ecantor.exchange;

import pl.lukbed.ecantor.cantor.CantorResourcesDto;
import pl.lukbed.ecantor.courses.CoursesStatus;
import pl.lukbed.ecantor.wallet.UserWalletDto;

import java.math.BigDecimal;

class SellValidator extends DealValidator {
    SellValidator(String code, int amount, CoursesStatus coursesStatus,
                  CantorResourcesDto cantorResources, UserWalletDto userWallet) {
        super(code, amount, coursesStatus, cantorResources, userWallet);
    }

    @Override
    boolean validate() {
        return isCurrencyAndAmountOk() && hasCantorAmount() && hasUserAmount();
    }

    @Override
    protected BigDecimal calcStandardCurrencyAmount(int amount) {
        BigDecimal purchasePriceForOneAmount = course.getPurchasePriceForOneAmount();
        return calcCurrencyAmount(purchasePriceForOneAmount, amount);
    }

    private boolean hasCantorAmount() {
        BigDecimal cantorAmount = cantorResources.getAmount("PLN");
        return cantorAmount.compareTo(standardCurrencyAmount) >= 0;
    }

    private boolean hasUserAmount() {
        BigDecimal userAmount = userWallet.getAmount(code);
        BigDecimal targetAmount = BigDecimal.valueOf(amount);
        return userAmount.compareTo(targetAmount) >= 0;
    }
}
