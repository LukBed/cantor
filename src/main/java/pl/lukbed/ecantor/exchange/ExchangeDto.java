package pl.lukbed.ecantor.exchange;

import com.google.common.collect.*;
import lombok.ToString;
import pl.lukbed.ecantor.cantor.CantorResourcesDto;
import pl.lukbed.ecantor.courses.CoursesStatus;
import pl.lukbed.ecantor.wallet.UserWalletDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@ToString
public class ExchangeDto {
    private final List<ExchangerCurrencyDto> currencies;
    private final LocalDateTime publicationData;

    ExchangeDto(UserWalletDto userWallet, CantorResourcesDto cantorResources, CoursesStatus coursesStatus) {
        var builder = new Builder(userWallet, cantorResources, coursesStatus);
        currencies = ImmutableList.copyOf(builder.build());
        publicationData = coursesStatus.getPublicationDate();
    }

    public List<ExchangerCurrencyDto> getCurrencies() {
        return currencies;
    }

    public List<ExchangerCurrencyDto> getCurrenciesForCantor() {
        return currencies.stream()
                .filter(currency -> !currency.getCode().equals("PLN"))
                .filter(currency -> currency.getAmountCantor().compareTo(BigDecimal.ZERO) > 0)
                .collect(Collectors.toList());
    }

    public List<ExchangerCurrencyDto> getCurrenciesForUser() {
        return currencies.stream()
                .filter(currency -> !currency.getCode().equals("PLN"))
                .filter(currency -> currency.getAmountUser().compareTo(BigDecimal.ZERO) > 0)
                .collect(Collectors.toList());
    }

    public BigDecimal getAvailableForUser() {
        return currencies.stream()
                .filter(currency -> currency.getCode().equals("PLN"))
                .findFirst()
                .map(ExchangerCurrencyDto::getAmountUser)
                .orElseGet(() -> BigDecimal.ZERO);
    }

    public LocalDateTime getPublicationData() {
        return publicationData;
    }

    private static class Builder {
        private Map<String, ExchangerCurrencyDto.Builder> currencyBuilders = Maps.newHashMap();

        private Builder(UserWalletDto userWallet, CantorResourcesDto cantorResources, CoursesStatus coursesStatus) {
            updateFromUserWallet(userWallet);
            updateFromCantorResources(cantorResources);
            updateFromCoursesStatus(coursesStatus);
            updateMainCurrency();
        }

        private List<ExchangerCurrencyDto> build() {
            return currencyBuilders
                    .values()
                    .stream()
                    .map(ExchangerCurrencyDto.Builder::build)
                    .sorted(Comparator.comparing(ExchangerCurrencyDto::getCode))
                    .collect(Collectors.toList());
        }

        private void updateFromUserWallet(UserWalletDto userWallet) {
            userWallet.getCurrencies()
                    .forEach(dto -> getCurrency(dto.getCurrencyCode()).setAmountUser(dto.getAmount()));
        }

        private void updateFromCantorResources(CantorResourcesDto cantorResources) {
            Map<String, BigDecimal> resources = cantorResources.getCurrencies();
            resources.keySet()
                    .stream()
                    .filter(code -> resources.get(code) != null && resources.get(code).compareTo(BigDecimal.ZERO)>0)
                    .forEach(code -> getCurrency(code).setAmountCantor(resources.get(code)));
        }

        private void updateFromCoursesStatus(CoursesStatus coursesStatus) {
            coursesStatus.getItems()
                    .forEach(course -> getCurrency(course.getCode()).
                                        setPurchasePrice(course.getPurchasePrice())
                                        .setSellPrice(course.getSellPrice())
                                        .setUnit(course.getUnit()));
        }

        private void updateMainCurrency() {
            currencyBuilders.get("PLN")
                    .setUnit(1)
                    .setSellPrice(BigDecimal.ONE)
                    .setPurchasePrice(BigDecimal.ONE);
        }

        private ExchangerCurrencyDto.Builder getCurrency(String code) {
            var currency = currencyBuilders.get(code);
            if (currency == null) {
                currency = ExchangerCurrencyDto.builder().setCode(code);
                currencyBuilders.put(code, currency);
            }
            return currency;
        }
    }
}
