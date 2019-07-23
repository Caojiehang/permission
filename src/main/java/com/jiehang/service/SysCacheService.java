package com.jiehang.service;

import com.google.common.base.Joiner;
import com.jiehang.beans.CacheKeyConstants;
import com.jiehang.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;

import javax.annotation.Resource;

/**
 * @ClassName SysCacheService
 * @Description TODO
 * @Author jiehangcao
 * @Date 2019-07-22 17:25
 **/
@Service
@Slf4j
public class SysCacheService {
    @Resource(name = "redisPool")
    private RedisPool redisPool;

    /**
     * add for null data
     * @param toSavedValue
     * @param timeoutSeconds
     * @param prefix
     */
    public void saveCache(String toSavedValue, int timeoutSeconds, CacheKeyConstants prefix) {
        saveCache(toSavedValue,timeoutSeconds,prefix,null);
    }

    /**
     * add data to cache
     * @param toSavedValue
     * @param timeoutSeconds
     * @param prefix
     * @param keys
     */
    public void saveCache(String toSavedValue, int timeoutSeconds, CacheKeyConstants prefix,String... keys) {
        if(toSavedValue == null) {
            return;
        }
        ShardedJedis shardedJedis = null;
        try {
            String cacheKey = generateCacheKey(prefix,keys);
            shardedJedis = redisPool.instance();
            shardedJedis.setex(cacheKey,timeoutSeconds,toSavedValue);
        }catch (Exception e) {
             log.error("save cache error,prefix:{},keys:{}",prefix.name(), JsonMapper.obj2String(keys),e);
        }finally {
            redisPool.safeClose(shardedJedis);
        }
    }

    /**
     * delete cache todo: consider
     * @param deleteValue
     * @param prefix
     * @param keys
     */
    public void deleteCache(String deleteValue, CacheKeyConstants prefix,String... keys) {
        if(deleteValue == null) {
            return;
        }
        ShardedJedis shardedJedis = null;
        try {
            String cacheKey = generateCacheKey(prefix,keys);
            shardedJedis = redisPool.instance();
            shardedJedis.del(cacheKey);
        }catch (Exception e) {
            log.error("delete cache failed, prefix:{}, keys:{}",prefix.name(),JsonMapper.obj2String(keys),e);
        }

    }

    /**
     * get data from cache
     * @param prefix
     * @param keys
     * @return
     */
    public String getFromCache(CacheKeyConstants prefix,String... keys) {
        ShardedJedis shardedJedis = null;
        String cacheKey = generateCacheKey(prefix,keys);
        try {
            shardedJedis = redisPool.instance();
            String value = shardedJedis.get(cacheKey);
            return value;
        }catch (Exception e) {
            log.error("get from cache exception, prefix:{}, keys:{}",prefix,JsonMapper.obj2String(keys),e);
            return null;
        }finally {
            redisPool.safeClose(shardedJedis);
        }

    }

    /**
     * generate key for storing data
     * @param prefix
     * @param keys
     * @return
     */
    private String generateCacheKey(CacheKeyConstants prefix, String ...keys) {
        String key = prefix.name();
        if(key != null && keys.length> 0) {
            key += "_"+ Joiner.on("_").join(keys);
        }
        return key;
    }
}
