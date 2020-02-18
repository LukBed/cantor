package pl.lukbed.ecantor.exchange;

import pl.lukbed.ecantor.cantor.CantorResourcesDto;
import pl.lukbed.ecantor.courses.CoursesStatus;
import pl.lukbed.ecantor.wallet.UserWalletDto;

abstract class Deal {
    protected final String code;
    protected final int foreignAmount;
    protected final ExchangeContext context;
    protected final CantorResourcesDto cantorResources;
    protected final UserWalletDto userWallet;
    protected final CoursesStatus courses;

    Deal(String code, int foreignAmount, ExchangeContext context) {
        this.code = code;
        this.foreignAmount = foreignAmount;
        this.context = context;
        cantorResources = context.getCantorService().getCantorResources();
        userWallet = context.getWalletService().getUserWallet();
        courses = context.getCoursesService().getCurrencyCourses();
    }
}
