package com.roopy.account.application.port.out;

import com.roopy.account.domain.Account;

public interface UpdateAccountStatePort {

    void updateActivities(Account account);

}
