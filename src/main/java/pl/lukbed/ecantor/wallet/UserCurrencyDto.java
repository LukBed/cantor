package pl.lukbed.ecantor.wallet;

import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
public class UserCurrencyDto {
    private final String currencyCode;
    private final BigDecimal amount;

    static UserCurrencyDto fromEntity(UserCurrencyEntity entity) {
        return new UserCurrencyDto(entity.getCurrencyCode(), entity.getAmount());
    }

    static UserCurrencyDto fromCodeAndAmount(String currencyCode, BigDecimal amount) {
        return new UserCurrencyDto(currencyCode, amount);
    }

    private UserCurrencyDto(String currencyCode, BigDecimal amount) {
        this.currencyCode = currencyCode;
        this.amount = amount;
    }
}
