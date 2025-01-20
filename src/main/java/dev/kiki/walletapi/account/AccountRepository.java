package dev.kiki.walletapi.account;

import dev.kiki.walletapi.account.dto.AccountStatistics;
import dev.kiki.walletapi.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    List <Account> findAllByUserId(UUID useId);

    Optional<Account> findByUser(User user);
    Optional<Account> findByUserAndId(User user, UUID id);

    @Query("""
        SELECT NEW dev.kiki.walletapi.account.dto.AccountStatistics(
            COALESCE(SUM(CASE WHEN t.type = 'OUT' THEN t.amount ELSE 0 END), 0),
            COALESCE(SUM(CASE WHEN t.type = 'IN' THEN t.amount ELSE 0 END), 0),
            a
        )
        FROM Account a
        LEFT JOIN Transaction t ON t.account.id = a.id
        WHERE a.id = :accountId
        GROUP BY a.id
        """)
    AccountStatistics getAccountStatistics(@Param("accountId") UUID accountId);

}
