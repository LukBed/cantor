package pl.lukbed.ecantor.wallet;

import com.google.common.collect.ImmutableSet;

import java.math.BigDecimal;
import java.util.Set;

public class UserWalletDto {
    private Set<UserCurrencyDto> currencies;

    UserWalletDto(Set<UserCurrencyDto> currencies) {
        this.currencies = currencies;
    }

    public Set<UserCurrencyDto> getCurrencies() {
        return ImmutableSet.copyOf(currencies);
    }

    public BigDecimal getAmount(String currencyCode) {
        return currencies.stream()
                .filter(currency -> currency.getCurrencyCode().equals(currencyCode))
                .findFirst()
                .map(UserCurrencyDto::getAmount)
                .orElse(BigDecimal.ZERO);
    }
}
