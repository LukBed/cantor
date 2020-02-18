package pl.lukbed.ecantor.exchange;

import pl.lukbed.ecantor.cantor.CantorResourcesDto;
import pl.lukbed.ecantor.courses.CoursesStatus;
import pl.lukbed.ecantor.wallet.UserWalletDto;

import java.math.*;

class BuyingValidator extends DealValidator{
    public BuyingValidator(String code, int amount,
                           CoursesStatus coursesStatus, CantorResourcesDto cantorResources, UserWalletDto userWallet) {
        super(code, amount, coursesStatus, cantorResources, userWallet);
    }

    @Override
    boolean validate() {
        return isCurrencyAndAmountOk() && hasCantorAmount() && hasUserAmount();
    }

    @Override
    protected BigDecimal calcStandardCurrencyAmount(int amount) {
        BigDecimal sellPriceForOneAmount = course.getSellPriceForOneAmount();
        return calcCurrencyAmount(sellPriceForOneAmount, amount);
    }
    private boolean hasCantorAmount() {
        BigDecimal cantorAmount = cantorResources.getAmount(code);
        BigDecimal targetAmount = BigDecimal.valueOf(amount);
        return cantorAmount.compareTo(targetAmount) >= 0;
    }

    private boolean hasUserAmount() {
        BigDecimal userAmount = userWallet.getAmount("PLN");
        return userAmount.compareTo(standardCurrencyAmount) >= 0;
    }
}
