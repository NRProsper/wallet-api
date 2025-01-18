package dev.kiki.walletapi.transaction;

import dev.kiki.walletapi.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final UserService userService;

    @GetMapping("/{account_id}")
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

}
