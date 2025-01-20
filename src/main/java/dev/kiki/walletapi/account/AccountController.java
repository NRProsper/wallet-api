package dev.kiki.walletapi.account;

import dev.kiki.walletapi.account.dto.AccountStatistics;
import dev.kiki.walletapi.account.dto.CreateAccountDto;
import dev.kiki.walletapi.account.dto.ExpenseDto;
import dev.kiki.walletapi.account.dto.TopupDto;
import dev.kiki.walletapi.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final UserService userService;


    @PostMapping
    public ResponseEntity<Account> createOne(
            @Valid @RequestBody CreateAccountDto accountDto
            ) {
        var authenticatedUser = userService.getAuthenticatedUser();
        var createdAccount = accountService.createAccount(accountDto, authenticatedUser);

        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    @DeleteMapping("/{account_id}")
    public ResponseEntity<Map<String, String>> delete(
            @PathVariable(name = "account_id") UUID accountId
    ) throws BadRequestException {
        var authenticatedUser = userService.getAuthenticatedUser();
        accountService.deleteAccount(authenticatedUser, accountId);

        var response = Map.of(
                "message" , "Account deleted successfully"
        );

        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);

    }

    @GetMapping
    public ResponseEntity<List<Account>> getAll() {
        var authenticatedUser = userService.getAuthenticatedUser();
        List<Account> accounts = accountService.getUserAccounts(authenticatedUser);

        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping("/{account_id}")
    public ResponseEntity<Account> getOne(
            @PathVariable(name = "account_id") UUID accountId
    ) {
        var authenticatedUser = userService.getAuthenticatedUser();

        var account = accountService.getUserAccount(authenticatedUser, accountId);

        return ResponseEntity.ok(account);
    }

    @PostMapping("/{account_id}/top-up")
    public ResponseEntity<Map<String, String>> top_up(
            @PathVariable(name = "account_id") UUID accountId,
            @Valid @RequestBody TopupDto dto
            ) throws BadRequestException {
        var authenticatedUser = userService.getAuthenticatedUser();
        var response = Map.of(
                "message" , "Top-up successful"
        );

        accountService.top_upAccount(authenticatedUser, accountId, dto);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/{account_id}/spend")
    public ResponseEntity<Map<String, String>> spend(
            @PathVariable(name = "account_id") UUID accountId,
            @Valid @RequestBody ExpenseDto dto
            ) {

        var authenticatedUser = userService.getAuthenticatedUser();
        accountService.spendMoney(accountId, authenticatedUser, dto);

        return ResponseEntity.ok(Map.of("message", "Expense recorded successfully"));

    }

    @GetMapping("/{account_id}/statistics")
    public ResponseEntity<AccountStatistics> getStatistics(
            @PathVariable(name = "account_id") UUID accountId
    ) throws BadRequestException {
        var authenticatedUser = userService.getAuthenticatedUser();
        AccountStatistics statistics = accountService.getAccountStatistics(authenticatedUser, accountId);
        return ResponseEntity.ok(statistics);
    }

}
