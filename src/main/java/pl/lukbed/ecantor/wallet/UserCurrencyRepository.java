package pl.lukbed.ecantor.wallet;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

interface UserCurrencyRepository extends JpaRepository<UserCurrencyEntity, Long> {
    List<UserCurrencyEntity> findAllByUserId(long userId);
    Optional<UserCurrencyEntity> findFirstByUserIdAndCurrencyCode(Long userId, String currencyCode);
}
