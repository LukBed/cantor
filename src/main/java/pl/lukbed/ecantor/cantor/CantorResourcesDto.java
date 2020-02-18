package pl.lukbed.ecantor.cantor;

import com.google.common.collect.*;

import java.math.BigDecimal;
import java.util.*;

public class CantorResourcesDto {
    private final Map<String, BigDecimal> currencies = Maps.newHashMap();

    CantorResourcesDto(List<CantorCurrencyEntity> entitiesList) {
        entitiesList.forEach(entity -> currencies.put(entity.getCurrencyCode(), entity.getAmount()));
    }

    public Map<String, BigDecimal> getCurrencies() {
        return ImmutableMap.copyOf(currencies);
    }

    public BigDecimal getAmount(String code) {
        BigDecimal amount = currencies.get(code);
        if (amount == null) {
            return BigDecimal.ZERO;
        }
        return amount;
    }
}
