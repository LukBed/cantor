package pl.lukbed.ecantor.cantor;

import lombok.Data;
import org.checkerframework.common.aliasing.qual.Unique;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
class CantorCurrencyEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Unique private String currencyCode;
    private BigDecimal amount;

    static CantorCurrencyEntity ofCodeAndAmount(String code, BigDecimal amount) {
        var currency = new CantorCurrencyEntity();
        currency.setCurrencyCode(code);
        currency.setAmount(amount);
        return currency;
    }
}
