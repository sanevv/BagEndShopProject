package com.github.semiprojectshop;

import com.github.semiprojectshop.config.cool.CoolSmsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
@EnableConfigurationProperties(CoolSmsProperties.class)// 프로퍼티스 스캔 야믈의 값 가져오는
public class SemiProjectShopSpringApplication {


    public static void main(String[] args) {
        SpringApplication.run(SemiProjectShopSpringApplication.class, args);
    }

}
