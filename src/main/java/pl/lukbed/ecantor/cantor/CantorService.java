package pl.lukbed.ecantor.cantor;

import java.math.BigDecimal;
import java.util.List;

public class CantorService {
    private final CantorCurrencyRepository currencyRepository;

    CantorService(CantorCurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public BigDecimal getAmountOfCurrency(String currencyCode) {
        return currencyRepository.findByCurrencyCode(currencyCode)
                .map(CantorCurrencyEntity::getAmount)
                .orElse(BigDecimal.ZERO);
    }

    public void subtractCurrency(String currencyCode, BigDecimal amount) {
        var currency = currencyRepository.findByCurrencyCode(currencyCode)
                .filter(c -> c.getAmount().compareTo(amount)>=0)
                .orElseThrow(() -> new IllegalStateException("Not enough " + currencyCode + " in cantor or currency not found"));
        currency.setAmount(currency.getAmount().subtract(amount));
        currencyRepository.save(currency);
    }

    public void addCurrency(String currencyCode, BigDecimal amount) {
        var currency = currencyRepository.findByCurrencyCode(currencyCode)
                .orElseThrow(() -> new IllegalStateException("Currency " + currencyCode + " not found in cantor"));
        currency.setAmount(currency.getAmount().add(amount));
        currencyRepository.save(currency);
    }

    public CantorResourcesDto getCantorResources() {
        List<CantorCurrencyEntity> currencies = currencyRepository.findAll();
        return new CantorResourcesDto(currencies);
    }
}
