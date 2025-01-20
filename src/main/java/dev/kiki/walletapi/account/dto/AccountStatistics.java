package dev.kiki.walletapi.account.dto;

import dev.kiki.walletapi.account.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@EqualsAndHashCode
public class AccountStatistics {
    private BigDecimal totalExpenses;
    private BigDecimal totalIncomes;
    private Account account;


}
