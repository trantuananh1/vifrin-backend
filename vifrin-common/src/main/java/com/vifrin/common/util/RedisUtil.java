package com.vifrin.common.util;

import redis.clients.jedis.Jedis;

/**
 * @author: tranmanhhung
 * @since: Fri, 10/12/2021
 **/

public class RedisUtil {
    private static RedisUtil redisUtil = new RedisUtil();
    public static RedisUtil getInstance() {
        return redisUtil;
    }

    private RedisUtil() {
    }

    public void setKeyValue(String key, String value){
        Jedis jedis = new Jedis();
        jedis.set(key, value);
    }

    public String getValue(String key){
        Jedis jedis = new Jedis();
        return jedis.get(key);
    }
}
