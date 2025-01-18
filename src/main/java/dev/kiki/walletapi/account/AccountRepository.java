package dev.kiki.walletapi.account;

import dev.kiki.walletapi.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    List <Account> findAllByUserId(UUID useId);

    Optional<Account> findByUser(User user);

}
