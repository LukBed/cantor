package pl.lukbed.ecantor.exchange;

import pl.lukbed.ecantor.application.ActionResponse;

class Seller extends Dealer {
    public Seller(ExchangeContext context) {
        super(context);
    }

    @Override
    protected ActionResponse deal(String code, int foreignAmount) {
        return new Sale(code, foreignAmount, context).execute();
    }
}
