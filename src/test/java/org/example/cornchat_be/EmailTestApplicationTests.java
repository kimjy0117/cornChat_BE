package org.example.cornchat_be;

import org.example.cornchat_be.util.redis.RedisUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EmailTestApplicationTests {
    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void redisTest() {
        //given
        String email = "test@test.com";
        String code = "111111";

        //when
        redisUtil.setData(email, code);

        //then
        Assertions.assertTrue(redisUtil.existData("test@test.com"));
        Assertions.assertFalse(redisUtil.existData("test1@test.com"));
        Assertions.assertEquals(redisUtil.getData(email), "111111");
    }
}
