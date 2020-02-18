package pl.lukbed.ecantor.exchange;

import pl.lukbed.ecantor.cantor.CantorResourcesDto;
import pl.lukbed.ecantor.courses.Course;
import pl.lukbed.ecantor.courses.CoursesStatus;
import pl.lukbed.ecantor.wallet.UserWalletDto;

import java.math.BigDecimal;
import java.math.RoundingMode;

abstract class DealValidator {
    protected final String code;
    protected final int amount;
    protected final Course course;
    protected final CantorResourcesDto cantorResources;
    protected final UserWalletDto userWallet;
    protected final BigDecimal standardCurrencyAmount;

    DealValidator(String code, int amount, CoursesStatus coursesStatus,
                         CantorResourcesDto cantorResources, UserWalletDto userWallet) {
        this.code = code;
        this.amount = amount;
        this.cantorResources = cantorResources;
        this.userWallet = userWallet;

        course = coursesStatus.getCourseOfCode(code)
                .orElseThrow(() -> new IllegalStateException("Course of " + code + " not found"));

        this.standardCurrencyAmount = calcStandardCurrencyAmount(amount);
    }

    BigDecimal getStandardCurrencyAmount() {
        return standardCurrencyAmount;
    }

    abstract boolean validate();

    protected abstract BigDecimal calcStandardCurrencyAmount(int amount);

    protected boolean isCurrencyAndAmountOk() {
        return amount>0 && isCurrencyNotBasic() && isAmountCorrectForUnit();
    }

    protected BigDecimal calcCurrencyAmount(BigDecimal price, int amount) {
        BigDecimal finalSellPrice = price.multiply(BigDecimal.valueOf(amount));
        return finalSellPrice.setScale(2, RoundingMode.UP);
    }

    private boolean isCurrencyNotBasic() {
        return !code.equals("PLN");
    }

    private boolean isAmountCorrectForUnit() {
        return amount%course.getUnit() == 0;
    }

}
