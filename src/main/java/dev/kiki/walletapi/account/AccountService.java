package dev.kiki.walletapi.account;

import dev.kiki.walletapi.account.dto.AccountStatistics;
import dev.kiki.walletapi.account.dto.CreateAccountDto;
import dev.kiki.walletapi.account.dto.ExpenseDto;
import dev.kiki.walletapi.account.dto.TopupDto;
import dev.kiki.walletapi.category.Category;
import dev.kiki.walletapi.category.CategoryRepository;
import dev.kiki.walletapi.exception.ResourceNotFoundException;
import dev.kiki.walletapi.transaction.Transaction;
import dev.kiki.walletapi.transaction.TransactionRepository;
import dev.kiki.walletapi.transaction.TransactionType;
import dev.kiki.walletapi.user.User;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;


    public Account createAccount(CreateAccountDto accountDto, User authenticatedUser) {
        var account = new Account();

        account.setType(accountDto.type());
        account.setBalance(accountDto.balance());
        account.setUser(authenticatedUser);

        return accountRepository.save(account);
    }

    public List<Account> getUserAccounts(User authenticatedUser) {
        return accountRepository.findAllByUserId(authenticatedUser.getId());
    }

    public Account getUserAccount(User authenticatedUser, UUID accountId) {
        var account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account cannot be found"));
        if(!authenticatedUser.getAccounts().contains(account)) {
            throw new RuntimeException("You do not have access to this account");
        }

        return account;
    }

    public void deleteAccount(User authenticatedUser, UUID accountId) throws BadRequestException {
        var account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account does not exist"));

        if(!account.getUser().getId().equals(authenticatedUser.getId())) {
            throw new BadRequestException("Account does not belong to the authenticated user");
        }

        accountRepository.delete(account);

    }

    public void top_upAccount(User authenticatedUser, UUID accountId, TopupDto dto) throws BadRequestException {
        var account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account does not exist"));

        if(!account.getUser().getId().equals(authenticatedUser.getId())) {
            throw new BadRequestException("Account does not belong to the authenticated user");
        }

        BigDecimal newBalance = account.getBalance().add(dto.amount());
        account.setBalance(newBalance);

        accountRepository.save(accountRepository.save(account));

        Transaction transaction = new Transaction();
        transaction.setAmount(dto.amount());
        transaction.setType(TransactionType.IN);
        transaction.setAccount(account);
        transaction.setCategory(null);

        transactionRepository.save(transaction);

    }

    public void spendMoney(UUID accountId, User authenticatedUser, ExpenseDto dto) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        if (!account.getUser().getId().equals(authenticatedUser.getId())) {
            throw new RuntimeException("Account does not belong to the authenticated user");
        }

        Category category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        if (!category.getUser().getId().equals(authenticatedUser.getId())) {
            throw new RuntimeException("Category does not belong to the authenticated user");
        }

        BigDecimal newBalance = account.getBalance().subtract(dto.amount());

        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        account.setBalance(newBalance);
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setAmount(dto.amount());
        transaction.setType(TransactionType.OUT);
        transaction.setAccount(account);
        transaction.setCategory(category);

        transactionRepository.save(transaction);

    }

    public AccountStatistics getAccountStatistics(User user, UUID accountId) throws BadRequestException {
        Account account = accountRepository.findByUserAndId(user, accountId)
                .orElseThrow(() -> new BadRequestException("Account not found or does not belong to the user"));


        return accountRepository.getAccountStatistics(accountId);
    }



}
