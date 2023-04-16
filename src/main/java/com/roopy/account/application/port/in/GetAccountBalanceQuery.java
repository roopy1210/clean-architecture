package com.roopy.account.application.port.in;

import com.roopy.account.domain.Account;
import com.roopy.account.domain.Money;

public interface GetAccountBalanceQuery {

    Money getAccountBalance(Account.AccountId accountId);

}
