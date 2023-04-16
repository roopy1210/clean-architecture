package com.roopy;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "roopy")
public class RoopyConfigurationProperties {
    private long transferThreshold = Long.MAX_VALUE;
}
