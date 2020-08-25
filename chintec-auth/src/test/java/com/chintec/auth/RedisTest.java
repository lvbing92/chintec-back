package com.chintec.auth;

import com.chintec.common.util.PhoneUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/8/17 9:50
 */
@Slf4j
@SpringBootTest
class RedisTest {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testPhone(){

        assert PhoneUtils.phoneCheck("13434343434");
    }
    @Test
    void redisTest01() {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        assert Objects.equals(valueOperations.get("18206116926"), "3644");
    }

    @Test
    void redisTest02() {
        Boolean delete = redisTemplate.delete("1");
        assert delete;
    }

    @Test
    void redisTest03() {
        log.info(String.valueOf(stringRedisTemplate.getExpire("name", TimeUnit.SECONDS)));
    }

    @Test
    void redisTest04() {
        ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
        stringStringValueOperations.set("name", "rubin");
        stringRedisTemplate.expire("name", 30, TimeUnit.SECONDS);
    }

    @Test
    void redisTest05() {
        SetOperations<String, String> stringStringSetOperations = stringRedisTemplate.opsForSet();
        stringStringSetOperations.add("names", "rubin", "jeff");
        stringRedisTemplate.expire("names", 60, TimeUnit.SECONDS);
    }
    @Test
    void redisTest06() {
        SetOperations<String, String> stringStringSetOperations = stringRedisTemplate.opsForSet();
        Objects.requireNonNull(stringStringSetOperations.members("names")).forEach(System.out::println);
    }
}
