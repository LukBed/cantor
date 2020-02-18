package pl.lukbed.ecantor.exchange;

import pl.lukbed.ecantor.application.ActionResponse;

class ExchangeService {
    private final ExchangeContext context;
    private final Buyer buyer;
    private final Seller seller;

    ExchangeService(ExchangeContext context, Buyer buyer, Seller seller) {
        this.context = context;
        this.buyer = buyer;
        this.seller = seller;
    }

    ExchangeDto getExchange() {
        var userWallet = context.getWalletService().getUserWallet();
        var cantorResources = context.getCantorService().getCantorResources();
        var courses = context.getCoursesService().getCurrencyCourses();
        return new ExchangeDto(userWallet, cantorResources, courses);
    }

    //this method should be executed in transaction
    ActionResponse buy(String code, int amount) {
        return buyer.execute(code, amount);
    }

    //this method should be executed in transaction
    ActionResponse sell(String code, int amount) {
        return seller.execute(code, amount);
    }
}