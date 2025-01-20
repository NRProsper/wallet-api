package dev.kiki.walletapi.transaction;

import dev.kiki.walletapi.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transactions")
@Tag(name = "Transaction Management", description = "Endpoints for managing user transactions")
@SecurityRequirement(name = "auth")
public class TransactionController {

    private final TransactionService transactionService;
    private final UserService userService;

    @GetMapping("/{account_id}")
    @Operation(
            summary = "Get paginated transactions for an account",
            description = "Retrieves a paginated list of transactions for a specific account, sorted by creation date in descending order."
    )
    public ResponseEntity<Page<Transaction>> getAll(
            @PathVariable(name = "account_id")UUID accountId,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size
            ) {

        if (page < 1) {
            throw new IllegalArgumentException("Page number must be greater than or equal to 1");
        }

        int springPage = page - 1;
        Pageable pageable = PageRequest.of(springPage, size, Sort.by("createdAt").descending());

        var authenticatedUser = userService.getAuthenticatedUser();

        var transactions = transactionService.getTransactions(pageable, accountId, authenticatedUser);

        return  ResponseEntity.ok(transactions);
    }

    @GetMapping("/all")
    @Operation(
            summary = "Get all transactions for the authenticated user",
            description = "Retrieves a list of all transactions for the authenticated user."
    )
    public ResponseEntity<List<Transaction>> getAllTransactionsForUser() {
        var authenticatedUser = userService.getAuthenticatedUser();
        var transactions = transactionService.getAllTransactionsForUser(authenticatedUser);
        return ResponseEntity.ok(transactions);
    }

}
