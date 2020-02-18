package pl.lukbed.ecantor.wallet;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.lukbed.ecantor.users.UserService;

@Configuration
class WalletConfiguration {
    @Bean
    WalletService createService(UserService userService,
                                UserCurrencyRepository userCurrencyRepository) {
        return new WalletService(userService, userCurrencyRepository);
    }
}
