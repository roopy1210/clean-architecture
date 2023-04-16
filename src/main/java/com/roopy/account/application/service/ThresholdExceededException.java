package com.roopy.account.application.service;

import com.roopy.account.domain.Money;

public class ThresholdExceededException extends RuntimeException {
    public ThresholdExceededException(Money threshold, Money actual) {
        super(String.format("Maximum threshold for transferring money exeeded: tried to transfer %s but threshold is %s!", actual, threshold));
    }
}
