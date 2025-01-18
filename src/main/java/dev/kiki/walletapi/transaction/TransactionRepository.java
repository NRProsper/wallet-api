package dev.kiki.walletapi.transaction;

import dev.kiki.walletapi.account.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    Page<Transaction> findByAccount(Account account, Pageable pageable);

}
