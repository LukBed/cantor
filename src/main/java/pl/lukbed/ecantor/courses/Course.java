package pl.lukbed.ecantor.courses;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class Course {
    private String name;
    private String code;
    private int unit;
    private BigDecimal purchasePrice;
    private BigDecimal sellPrice;

    public BigDecimal getPurchasePriceForOneAmount() {
        return getPriceToForOneAmount(purchasePrice);
    }

    public BigDecimal getSellPriceForOneAmount() {
        return getPriceToForOneAmount(sellPrice);
    }

    private BigDecimal getPriceToForOneAmount(BigDecimal price) {
        if (unit == 1) {
            return price;
        } else {
            return price.divide(BigDecimal.valueOf(unit));
        }
    }
}
