package dev.kiki.walletapi.account;

import dev.kiki.walletapi.account.dto.AccountStatistics;
import dev.kiki.walletapi.account.dto.CreateAccountDto;
import dev.kiki.walletapi.account.dto.ExpenseDto;
import dev.kiki.walletapi.account.dto.TopupDto;
import dev.kiki.walletapi.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Account Management", description = "Endpoints for managing user accounts")
@SecurityRequirement(name = "auth")
public class AccountController {

    private final AccountService accountService;
    private final UserService userService;


    @PostMapping
    @Operation(
            summary = "Create a new account",
            description = "Creates a new account for the authenticated user."
    )
    public ResponseEntity<Account> createOne(
            @Valid @RequestBody CreateAccountDto accountDto
            ) {
        var authenticatedUser = userService.getAuthenticatedUser();
        var createdAccount = accountService.createAccount(accountDto, authenticatedUser);

        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    @DeleteMapping("/{account_id}")
    @Operation(
            summary = "Delete an account",
            description = "Deletes an account by its ID for the authenticated user."
    )
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
    @Operation(
            summary = "Get all accounts",
            description = "Retrieves all accounts for the authenticated user."
    )
    public ResponseEntity<List<Account>> getAll() {
        var authenticatedUser = userService.getAuthenticatedUser();
        List<Account> accounts = accountService.getUserAccounts(authenticatedUser);

        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping("/{account_id}")
    @Operation(
            summary = "Get an account by ID",
            description = "Retrieves an account by its ID for the authenticated user."
    )
    public ResponseEntity<Account> getOne(
            @PathVariable(name = "account_id") UUID accountId
    ) {
        var authenticatedUser = userService.getAuthenticatedUser();

        var account = accountService.getUserAccount(authenticatedUser, accountId);

        return ResponseEntity.ok(account);
    }

    @PostMapping("/{account_id}/top-up")
    @Operation(
            summary = "Top up an account",
            description = "Adds funds to an account for the authenticated user."
    )
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
    @Operation(
            summary = "Top up an account",
            description = "Adds funds to an account for the authenticated user."
    )
    public ResponseEntity<Map<String, String>> spend(
            @PathVariable(name = "account_id") UUID accountId,
            @Valid @RequestBody ExpenseDto dto
            ) {

        var authenticatedUser = userService.getAuthenticatedUser();
        accountService.spendMoney(accountId, authenticatedUser, dto);

        return ResponseEntity.ok(Map.of("message", "Expense recorded successfully"));

    }

    @GetMapping("/{account_id}/statistics")
    @Operation(
            summary = "Get account statistics",
            description = "Retrieves statistics for an account, such as total income, expenses, and balance."
    )
    public ResponseEntity<AccountStatistics> getStatistics(
            @PathVariable(name = "account_id") UUID accountId
    ) throws BadRequestException {
        var authenticatedUser = userService.getAuthenticatedUser();
        AccountStatistics statistics = accountService.getAccountStatistics(authenticatedUser, accountId);
        return ResponseEntity.ok(statistics);
    }

}
