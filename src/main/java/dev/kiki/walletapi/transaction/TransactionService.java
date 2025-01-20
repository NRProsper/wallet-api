package dev.kiki.walletapi.transaction;

import dev.kiki.walletapi.account.Account;
import dev.kiki.walletapi.account.AccountRepository;
import dev.kiki.walletapi.exception.ResourceNotFoundException;
import dev.kiki.walletapi.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public Page<Transaction> getTransactions(
            Pageable pageable, UUID accountId, User authenticatedUser
    ) {
        var account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account does not exist"));

        if(!account.getUser().getId().equals(authenticatedUser.getId())) {
            throw new RuntimeException("You do not have access to this account");
        }

        return transactionRepository.findByAccount(account, pageable);

    }


    public List<Transaction> getAllTransactionsForUser(User authenticatedUser) {
        List<Account> userAccounts = accountRepository.findAllByUserId(authenticatedUser.getId());
        return transactionRepository.findByAccountIn(userAccounts);
    }

}
