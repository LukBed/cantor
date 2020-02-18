package pl.lukbed.ecantor.exchange;

import pl.lukbed.ecantor.application.ActionResponse;

class Buyer extends Dealer {

    public Buyer(ExchangeContext context) {
        super(context);
    }

    @Override
    protected ActionResponse deal(String code, int foreignAmount) {
        return new Buying(code, foreignAmount, context).execute();
    }
}