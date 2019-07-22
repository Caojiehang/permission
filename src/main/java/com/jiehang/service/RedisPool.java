package com.jiehang.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import javax.annotation.Resource;

/**
 * @ClassName RedisPool
 * @Description TODO
 * @Author jiehangcao
 * @Date 2019-07-22 17:22
 **/
@Service("redisPool")
@Slf4j
public class RedisPool {

    @Resource(name = "shardedJedisPool")
    private ShardedJedisPool shardedJedisPool;

    /**
     * instance redis to build connection
     * @return
     */
    public ShardedJedis instance() {
        return shardedJedisPool.getResource();
    }

    /**
     * close redis connect
     * @param shardedJedis
     */
    public void safeClose(ShardedJedis shardedJedis) {
        try {
            if(shardedJedis != null) {
                shardedJedis.close();
            }
        } catch (Exception e) {
            log.error("return redis resource exception",e);
        }
    }
}
