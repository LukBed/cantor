package pl.lukbed.ecantor.exchange;

import pl.lukbed.ecantor.application.ActionResponse;

abstract class Dealer {
    protected final ExchangeContext context;

    Dealer(ExchangeContext context) {
        this.context = context;
    }

    protected ActionResponse execute(String code, int foreignAmount) {
        try {
            return deal(code, foreignAmount);
        } catch (IllegalStateException exception) {
            return ActionResponse.ofMessage(exception.getMessage());
        }
    }

    protected abstract ActionResponse deal(String code, int foreignAmount);
}
