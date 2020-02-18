package pl.lukbed.ecantor.exchange;

import lombok.AllArgsConstructor;
import lombok.Value;
import pl.lukbed.ecantor.cantor.CantorService;
import pl.lukbed.ecantor.courses.CoursesService;
import pl.lukbed.ecantor.wallet.WalletService;

@Value
@AllArgsConstructor
class ExchangeContext {
    private final CantorService cantorService;
    private final WalletService walletService;
    private final CoursesService coursesService;
}