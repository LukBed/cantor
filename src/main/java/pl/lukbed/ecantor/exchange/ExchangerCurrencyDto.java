package pl.lukbed.ecantor.exchange;

import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@ToString
public class ExchangerCurrencyDto {
    private final String code;
    private final int unit;
    private final BigDecimal amountCantor;
    private final BigDecimal amountUser;
    private final BigDecimal purchasePrice;
    private final BigDecimal sellPrice;

    static Builder builder() {
        return new Builder();
    }

    private ExchangerCurrencyDto(String code, int unit, BigDecimal amountCantor, BigDecimal amountUser,
                                 BigDecimal purchasePrice, BigDecimal sellPrice) {
        this.code = code;
        this.unit = unit;
        this.amountCantor = amountCantor;
        this.amountUser = amountUser;
        this.purchasePrice = purchasePrice;
        this.sellPrice = sellPrice;
    }

    public BigDecimal getUserValue() {
        return amountUser.multiply(purchasePrice).setScale(2, RoundingMode.UP);
    }

    static class Builder {
        private String code;
        private Integer unit;
        private BigDecimal amountCantor = BigDecimal.ZERO;
        private BigDecimal amountUser = BigDecimal.ZERO;
        private BigDecimal purchasePrice;
        private BigDecimal sellPrice;

        private Builder() {
        }

        ExchangerCurrencyDto build() {
            if (unit == null) {
                throw new IllegalStateException("Unit of currency not defined");
            } else if (code == null) {
                throw new IllegalStateException("Code of currency not defined");
            } if (purchasePrice == null) {
                throw new IllegalStateException("Purchase price of currency not defined");
            } if (sellPrice == null) {
                throw new IllegalStateException("Sell price price of currency not defined");
            }

            return new ExchangerCurrencyDto(code, unit, amountCantor, amountUser, purchasePrice, sellPrice);
        }

        Builder setCode(String code) {
            this.code = code;
            return this;
        }

        Builder setUnit(int unit) {
            this.unit = unit;
            return this;
        }

        Builder setAmountCantor(BigDecimal amountCantor) {
            this.amountCantor = amountCantor;
            return this;
        }

        Builder setAmountUser(BigDecimal amountUser) {
            this.amountUser = amountUser;
            return this;
        }

        Builder setPurchasePrice(BigDecimal purchasePrice) {
            this.purchasePrice = purchasePrice;
            return this;
        }

        Builder setSellPrice(BigDecimal sellPrice) {
            this.sellPrice = sellPrice;
            return this;
        }
    }
}
