package com.permission.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import javax.annotation.Resource;


/**
 * redis线程池
 *
 * @author zt1994 2018/11/22 20:54
 */
@Service("redisPool")
public class RedisPool {

    @Resource(name = "shardedJedisPool")
    private ShardedJedisPool shardedJedisPool;

    private static final Logger LOGGER = LoggerFactory.getLogger("RedisPool");

    /**
     * 获取实例
     */
    public ShardedJedis instance() {
        return shardedJedisPool.getResource();
    }

    public void safeClose(ShardedJedis shardedJedis) {
        try {
            if (shardedJedis != null) {
                shardedJedis.close();
            }

        } catch (Exception e) {
            LOGGER.error("return redis resource exception", e);
        }
    }
}

