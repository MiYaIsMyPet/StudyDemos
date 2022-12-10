package com.miya.redisdemo;

import com.miya.redisdemo.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @ClassName RedisDemoApplicationTests.java
 * @Author miya
 * @CreatTime 2022年12月10日22:11:14
 * @Version 1.0
 **/
@SpringBootTest
class RedisDemoApplicationTests {

    @Autowired
    RedisUtil redisUtil;
    @Test
    void setValue() {
        redisUtil.set("user1","xiekun");
        redisUtil.setWithExpire("user2","chenyazhen",1);
    }

    @Test
    void getValue() {
        System.out.println("value:"+redisUtil.get("user1"));
        System.out.println("value:"+redisUtil.get("user2"));
    }

}
