package pl.lukbed.ecantor.wallet;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
class UserCurrencyEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private String currencyCode;
    private BigDecimal amount;
    private Long userId; //no relation n:1 for better encapsulation (UserEntity package-private)

    static UserCurrencyEntity ofCodeAmountAndUserId(String currencyCode, BigDecimal amount, Long userId) {
        return new UserCurrencyEntity(currencyCode, amount, userId);
    }

    UserCurrencyEntity() {
    }

    private UserCurrencyEntity(String currencyCode, BigDecimal amount, Long userId) {
        this.currencyCode = currencyCode;
        this.amount = amount;
        this.userId = userId;
    }
}
