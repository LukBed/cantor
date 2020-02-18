package pl.lukbed.ecantor.wallet;

import pl.lukbed.ecantor.users.UserService;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

public class WalletService {
    private final UserService userService;
    private final UserCurrencyRepository userCurrencyRepository;

    public WalletService(UserService userService,
                  UserCurrencyRepository userCurrencyRepository) {
        this.userService = userService;
        this.userCurrencyRepository = userCurrencyRepository;
    }

    public UserWalletDto getUserWallet() {
        Set<UserCurrencyDto> currencies = userCurrencyRepository.findAllByUserId(getCurrentUserId())
                .stream()
                .map(UserCurrencyDto::fromEntity)
                .collect(Collectors.toSet());
        return new UserWalletDto(currencies);
    }

    public void addCurrency(String currencyCode, BigDecimal amount) {
        Long currentUserId = getCurrentUserId();
        UserCurrencyEntity currency = userCurrencyRepository
                .findFirstByUserIdAndCurrencyCode(currentUserId, currencyCode)
                .orElseGet(() -> UserCurrencyEntity.ofCodeAmountAndUserId(currencyCode, BigDecimal.ZERO, currentUserId));
        currency.setAmount(currency.getAmount().add(amount));
        userCurrencyRepository.save(currency);
    }

    public void subtractCurrency(String currencyCode, BigDecimal amount) {
        Long currentUserId = getCurrentUserId();
        UserCurrencyEntity currency = userCurrencyRepository
                .findFirstByUserIdAndCurrencyCode(currentUserId, currencyCode)
                .filter(c -> c.getAmount().compareTo(amount) >= 0)
                .orElseThrow(() ->  new IllegalStateException("Not enough " + currencyCode + " in wallet"));

        currency.setAmount(currency.getAmount().subtract(amount));
        userCurrencyRepository.save(currency);
    }

    private Long getCurrentUserId() {
        return userService.getCurrentUser().orElseThrow(() -> new IllegalStateException("User not signed in")).getId();
    }
}
