package com.roopy.account.application.domain;

import com.roopy.account.domain.Account;
import com.roopy.account.domain.ActivityWindow;
import com.roopy.account.domain.Money;
import org.junit.jupiter.api.Test;
import static com.roopy.common.AccountTestData.*;
import static com.roopy.common.ActivityTestData.*;
import static org.assertj.core.api.Assertions.*;

public class AccountTest {

    @Test
    void calculateBalance() {
        Account.AccountId accountId = new Account.AccountId(1L);
        Account account = defaultAccount()
                .withAccountId(accountId)
                .withBaselineBalance(Money.of(555L))
                .withActivityWindow(new ActivityWindow(
                        defaultActivity()
                                .withTargetAccount(accountId)
                                .withMoney(Money.of(999L)).build(),
                        defaultActivity()
                                .withTargetAccount(accountId)
                                .withMoney(Money.of(1L)).build()))
                .build();

        Money balance = account.calculateBalance();

        assertThat(balance).isEqualTo(Money.of(1555L));
    }

}
