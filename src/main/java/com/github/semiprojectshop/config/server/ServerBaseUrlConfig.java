package com.github.semiprojectshop.config.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class ServerBaseUrlConfig {
    @Bean//1번째 제네릭타입 입력값 2번째 제네릭 반환값 람다 함수에서의 반환값이 apply 메소드의 반환값
    public Function<ServerUrlFields, String> requestBaseUrlProvider() {
        return fields -> {

            String scheme = fields.getScheme();   // "http" 또는 "https"
            String serverName = fields.getServerName(); // "localhost" 또는 "simpcode.kro.kr"
            int serverPort = fields.getPort(); // 8080 또는 443 등

            String baseUrl = scheme + "://" + serverName;

            // 기본 포트(80, 443)가 아니라면 포트 추가
            if ((scheme.equals("http") && serverPort != 80) || (scheme.equals("https") && serverPort != 443)) {
                baseUrl += ":" + serverPort;
            }

            return baseUrl;
        };
    }
}
