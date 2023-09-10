package com.atguigu.testRedis;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;
import java.util.Set;

/**
 * @className: TestRedisConector
 * @Package: com.atguigu.testRedis

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring/spring-redis.xml")
public class TestRedisConector {

    @Autowired
    private JedisPool jedisPool;

    @Test
    public void t1() {
        Jedis jedis = jedisPool.getResource();
        log.debug("------> jedis={}", jedis);
        Set<String> keys = jedis.keys("*");
        keys.forEach(System.out::println);
        System.out.println("---------------------------------");

        Map<String, String> hgetAll = jedis.hgetAll("map:user1");
        System.out.println("hgetAll = " + hgetAll);
        jedis.close();
    }

}
