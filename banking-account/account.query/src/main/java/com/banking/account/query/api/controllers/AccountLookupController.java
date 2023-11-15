package com.banking.account.query.api.controllers;

import com.banking.account.query.api.dto.AccountLookupResponse;
import com.banking.account.query.api.dto.EqualityType;
import com.banking.account.query.api.queries.FindAccountByHolderQuery;
import com.banking.account.query.api.queries.FindAccountByIdQuery;
import com.banking.account.query.api.queries.FindAccountWithBalanceQuery;
import com.banking.account.query.api.queries.FindAllAccountsQuery;
import com.banking.account.query.domain.BankAccount;
import com.banking.cqrs.core.infrastructure.QueryDispatcher;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bankAccountLookup")
@AllArgsConstructor
public class AccountLookupController {
    private static final Logger LOG = LoggerFactory.getLogger(AccountLookupController.class);
    private final QueryDispatcher queryDispatcher;

    @GetMapping
    public ResponseEntity<AccountLookupResponse> getAllAccounts() {
        LOG.info("Querying for all accounts");
        try {
            List<BankAccount> accounts = queryDispatcher.sendQuery(new FindAllAccountsQuery());
            if (accounts.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(AccountLookupResponse.builder().accounts(accounts).build());
        } catch (Exception e) {
            LOG.error("Error while looking up accounts", e);
            return ResponseEntity.badRequest().body(new AccountLookupResponse("Error while looking up accounts"));
        }
    }

    @GetMapping(path = "/byId/{id}")
    public ResponseEntity<AccountLookupResponse> getAccountById(@PathVariable("id") String id) {
        LOG.info("Querying for account with id {}", id);
        try {
            List<BankAccount> accounts = queryDispatcher.sendQuery(new FindAccountByIdQuery(id));
            if (accounts.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(AccountLookupResponse.builder().accounts(accounts).build());
        } catch (Exception e) {
            LOG.error("Error while looking up account with id {}", id, e);
            return ResponseEntity.badRequest().body(new AccountLookupResponse("Error while looking up account with id " + id));
        }
    }

    @GetMapping(path = "/byHolder/{holder}")
    public ResponseEntity<AccountLookupResponse> getAccountByHolder(@PathVariable("holder") String holder) {
        LOG.info("Querying for account with holder {}", holder);
        try {
            List<BankAccount> accounts = queryDispatcher.sendQuery(new FindAccountByHolderQuery(holder));
            if (accounts.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(AccountLookupResponse.builder().accounts(accounts).build());
        } catch (Exception e) {
            LOG.error("Error while looking up account with holder {}", holder, e);
            return ResponseEntity.badRequest().body(new AccountLookupResponse("Error while looking up account with holder " + holder));
        }
    }

    @GetMapping(path = "/byBalance/{equalityType}/{balance}")
    public ResponseEntity<AccountLookupResponse> getAccountByBalance(
            @PathVariable("equalityType") EqualityType equalityType,
            @PathVariable("balance") BigDecimal balance) {
        LOG.info("Querying for account with balance {} {}", equalityType, balance);
        try {
            List<BankAccount> accounts = queryDispatcher.sendQuery(new FindAccountWithBalanceQuery(balance, equalityType));
            if (accounts.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(AccountLookupResponse.builder().accounts(accounts).build());
        } catch (Exception e) {
            LOG.error("Error while looking up account with balance {} {}", equalityType, balance, e);
            return ResponseEntity.badRequest().body(new AccountLookupResponse("Error while looking up account with balance " + equalityType + " " + balance));
        }
    }
}
