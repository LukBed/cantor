package pl.lukbed.ecantor.cantor;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.*;
import org.springframework.context.event.EventListener;

import java.math.BigDecimal;

@Configuration
class CantorConfiguration {
    private final CantorCurrencyRepository currencyRepository;

    CantorConfiguration(CantorCurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Bean
    CantorService cantorService() {
        return new CantorService(currencyRepository);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initialiseCantorCurrency() {
        initialiseSingleCurrency("PLN", BigDecimal.valueOf(1_000_000));
        initialiseSingleCurrency("USD", BigDecimal.valueOf(100_000));
        initialiseSingleCurrency("EUR", BigDecimal.valueOf(100_000));
        initialiseSingleCurrency("CHF", BigDecimal.valueOf(100_000));
        initialiseSingleCurrency("RUB", BigDecimal.valueOf(100_000));
        initialiseSingleCurrency("CZK", BigDecimal.valueOf(100_000));
        initialiseSingleCurrency("GBP", BigDecimal.valueOf(100_000));
    }

    private void initialiseSingleCurrency(String code, BigDecimal amount) {
        if (currencyRepository.findByCurrencyCode(code).isEmpty()) {
            currencyRepository.save(CantorCurrencyEntity.ofCodeAndAmount(code, amount));
        }
    }
}
