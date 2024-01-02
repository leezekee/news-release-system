package com.leezekee;

import com.leezekee.utils.Md5Util;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class NewsPublishBackendApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void generatePassword() {
        System.out.println(UUID.randomUUID());
    }

}
