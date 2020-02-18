package pl.lukbed.ecantor.exchange;

import org.springframework.context.annotation.*;
import pl.lukbed.ecantor.cantor.CantorService;
import pl.lukbed.ecantor.courses.CoursesService;
import pl.lukbed.ecantor.wallet.WalletService;

@Configuration
class ExchangeConfiguration {
    @Bean
    ExchangeService exchangeService(WalletService walletService,
                                          CantorService cantorService,
                                          CoursesService coursesService) {
        ExchangeFactory factory = new ExchangeFactory();
        return factory.createService(walletService, cantorService, coursesService);
    }
}
