package com.roopy.account.application.service;

import com.roopy.account.application.port.in.GetAccountBalanceQuery;
import com.roopy.account.application.port.out.LoadAccountPort;
import com.roopy.account.domain.Account;
import com.roopy.account.domain.Money;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class GetAccountBalanceService implements GetAccountBalanceQuery {

    private final LoadAccountPort loadAccountPort;

    @Override
    public Money getAccountBalance(Account.AccountId accountId) {
        return loadAccountPort.loadAccount(accountId, LocalDateTime.now()).calculateBalance();
    }
}
