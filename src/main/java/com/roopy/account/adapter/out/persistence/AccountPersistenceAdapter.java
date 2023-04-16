package com.roopy.account.adapter.out.persistence;

import com.roopy.account.application.port.out.LoadAccountPort;
import com.roopy.account.application.port.out.UpdateAccountStatePort;
import com.roopy.account.domain.Account;
import com.roopy.account.domain.Activity;
import com.roopy.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@PersistenceAdapter
@Slf4j
public class AccountPersistenceAdapter implements
                        LoadAccountPort,
                        UpdateAccountStatePort {

    private final SpringDataAccountRepository accountRepository;
    private final ActivityRespository activityRespository;
    private final AccountMapper accountMapper;

    @Override
    public Account loadAccount(Account.AccountId accountId,
                                LocalDateTime baselineDate) {

        log.info("[AccountPersistenceAdapter.loadAccount] accountId : {}, baselineDate : {}", accountId, baselineDate);

        AccountJpaEntity account = accountRepository.findById(accountId.getValue())
                                                    .orElseThrow(EntityNotFoundException::new);

        log.info("[AccountPersistenceAdapter.loadAccount] account : {}", account);

        List<ActivityJpaEntity> activities = activityRespository.findByOwnerSince(
                                                    accountId.getValue(),
                                                    baselineDate);

        log.info("[AccountPersistenceAdapter.loadAccount] activities : {}", activities);

        Long withdrawalBalance = orZero(activityRespository.getWithdrawalBalanceUntil(
                                                    accountId.getValue(),
                                                    baselineDate));

        log.info("[AccountPersistenceAdapter.loadAccount] withdrawalBalance : {}", withdrawalBalance);

        Long depositBalance = orZero(activityRespository.getDepositBalanceUntil(
                                                    accountId.getValue(),
                                                    baselineDate));

        log.info("[AccountPersistenceAdapter.loadAccount] depositBalance : {}", depositBalance);

        return accountMapper.mapToDomainEntity(
                account,
                activities,
                withdrawalBalance,
                depositBalance);
    }

    private Long orZero(Long value) {
        return value == null ? 0L : value;
    }

    @Override
    public void updateActivities(Account account) {
        for (Activity activity : account.getActivityWindow().getActivities()) {
            if (activity.getId() == null) {
                activityRespository.save(accountMapper.mapToJpaEntity(activity));
            }
        }
    }
}
