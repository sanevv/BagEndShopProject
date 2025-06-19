package com.github.semiprojectshop.config;

import com.github.semiprojectshop.config.encryption.AES256;
import com.github.semiprojectshop.config.encryption.SecretMyKey;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;

@Configuration
public class BeanProperties {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    //aes256 방식 = 양방향 암호화( 암호화 복호화 가능) 암호화키가 반드시필요함.
//bcrypt 방식 = 단방향 암호화( 암호화만 가능) 복호화 불가능. 암호화키가 필요없음.
    //sha256 방식 = 단방향 암호화( 암호화만 가능) 복호화 불가능. 암호화키가 필요없음.

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AES256 aes256() throws UnsupportedEncodingException {
        return new AES256(SecretMyKey.KEY);
    }
    public static String decryptNotException(String encrypted){
        try {
            AES256 aes256 = new AES256(SecretMyKey.KEY);
            return aes256.decrypt(encrypted);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("AES256 객체 생성 실패: " + e.getMessage());
        }
    }
}
