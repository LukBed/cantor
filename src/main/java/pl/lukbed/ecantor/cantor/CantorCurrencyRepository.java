package pl.lukbed.ecantor.cantor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface CantorCurrencyRepository extends JpaRepository<CantorCurrencyEntity, Long> {
    Optional<CantorCurrencyEntity> findByCurrencyCode(String currencyCode);
}
