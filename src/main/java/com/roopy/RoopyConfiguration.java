package com.roopy;

import com.roopy.account.application.service.MoneyTransferProperties;
import com.roopy.account.domain.Money;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(RoopyConfigurationProperties.class)
public class RoopyConfiguration {
    /**
     * Adds a use-case-specific {@link MoneyTransferProperties} object to the application context. The properties
     * are read from the Spring-Boot-specific {@link RoopyConfigurationProperties} object.
     */
    @Bean
    public MoneyTransferProperties moneyTransferProperties(RoopyConfigurationProperties roopyConfigurationProperties) {
        return new MoneyTransferProperties(Money.of(roopyConfigurationProperties.getTransferThreshold()));
    }
}
