package pl.lukbed.ecantor.exchange;

import pl.lukbed.ecantor.cantor.CantorService;
import pl.lukbed.ecantor.courses.CoursesService;
import pl.lukbed.ecantor.wallet.WalletService;

class ExchangeFactory {
    ExchangeService createService(WalletService walletService,
                                  CantorService cantorService,
                                  CoursesService coursesService) {
        ExchangeContext context = new ExchangeContext(cantorService, walletService, coursesService);
        Buyer buyer = new Buyer(context);
        Seller seller = new Seller(context);
        return new ExchangeService(context, buyer, seller);
    }
}
