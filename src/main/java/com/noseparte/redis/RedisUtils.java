package com.noseparte.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * Copyright © 2018 noseparte © BeiJing BoLuo Network Technology Co. Ltd.
 *
 * @Author Noseparte
 * @Compile 2018-12-27 -- 20:20
 * @Version 1.0
 * @Description     redis工具类 操作
 *                  string（字符串），hash（哈希），list（列表），set（集合）及zset(sorted set：有序集合)。
 */
@Component
public class RedisUtils {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    // ===================================================================
    //                       Common methods
    //======================================================================//
    /**
     * 设置失效时间
     *
     * @param key
     * @param time
     * @return
     */
    public Boolean expire(String key, long time){
        if(time > 0){
            redisTemplate.expire(key,time,TimeUnit.SECONDS);
            return true;
        }else{
            return false;
        }
    }

    /**
     * 根据key获取失效时间
     *
     * @param key
     * @return
     */
    public Long getExpireTime(String key){
        return redisTemplate.getExpire(key);
    }

    /**
     * 判断key是否存在
     *
     * @param key
     * @return
     */
    public Boolean hasKey(String key){
        return redisTemplate.hasKey(key);
    }

    /**
     * 删除缓存
     *
     * @param key
     */
    public void del(String... key){
        if(key != null && key.length > 0){
            if(key.length == 1){
                redisTemplate.delete(key[0]);
            }else{
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    // ===================================================================
    //                       String methods
    //======================================================================//

    /**
     * 普通缓存获取
     *
     * @param key
     * @return
     */
    public Object get(String key){
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入
     *
     * @param key
     * @param obj
     * @return
     */
    public Boolean set(String key, Object obj){
        if(hasKey(key)){
            redisTemplate.opsForValue().set(key, obj);
            return true;
        }else{
            return false;
        }
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key
     * @param obj
     * @param time
     * @return
     */
    public Boolean set(String key, Object obj, long time){
        if(hasKey(key)){
            redisTemplate.opsForValue().set(key, obj, time);
            return true;
        }else{
            return false;
        }
    }



}
