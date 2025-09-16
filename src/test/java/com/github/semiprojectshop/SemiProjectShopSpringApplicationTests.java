package com.github.semiprojectshop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "jasypt.encryptor.password=bagend")
class SemiProjectShopSpringApplicationTests {
    @Test
    void contextLoads() {}
}