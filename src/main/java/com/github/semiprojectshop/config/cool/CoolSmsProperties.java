package com.github.semiprojectshop.config.cool;

import io.lettuce.core.dynamic.annotation.CommandNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.stereotype.Component;
@ConfigurationProperties(prefix = "coolsms")
@AllArgsConstructor
@Getter
public class CoolSmsProperties {
    private final String apiKey;
    private final String apiSecret;
    private final String domain;
    private final String phoneNumber;

}
