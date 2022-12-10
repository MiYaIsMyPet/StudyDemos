package com.miya.redisdemo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName RedisUtil.java
 * @Author miya
 * @Description Redis的相关操作类
 * @CreatTime 2022年12月10日 18:24:00
 * @Version 1.0
 **/

@Component
public class RedisUtil {

    private static final Logger logger = LoggerFactory.getLogger(RedisUtil.class);
    @Resource
    RedisTemplate<String,Object> redisTemplate;


    /**
     * @title set
     * @descriptio 写入Redis缓存
     * @author miya
     * @params key,value
     * @updateTime 2022/12/10 18:36
     */
    public boolean set(final String key, Object value){
        boolean tag = false;
        try {
            ValueOperations<String,Object> valueOperations = redisTemplate.opsForValue();
            valueOperations.set(key,value);
            tag = true;
        } catch (Exception e) {
            logger.error("<--Redis execption:数据写入缓存失败，key:{},异常信息：{}-->",key,e.getMessage());
        }
        return tag;
    }


    /**
     * @title setWithExpire
     * @description 有过期时间的写入缓存
     * @author miya
     * @params key,value,expireTime
     * @updateTime 2022/12/10 18:37
     */
    public boolean setWithExpire(final String key, Object value,long expireTime){
        boolean tag = false;
        try {
            ValueOperations<String,Object> valueOperations = redisTemplate.opsForValue();
            valueOperations.set(key,value);
            redisTemplate.expire(key,expireTime, TimeUnit.SECONDS);
            tag = true;
        } catch (Exception e) {
            logger.error("<--Redis execption:数据写入过期时间缓存失败，key:{},异常信息：{}-->",key,e.getMessage());
        }
        return tag;
    }


    /**
     * @title getAndSet
     * @description 更新缓存
     * @author miya
     * @params key,value
     * @updateTime 2022/12/10 18:40
     */
    public boolean getAndSet(final String key,Object value){
        boolean tag = false;
        try {
            redisTemplate.opsForValue().getAndSet(key,value);
            tag = true;
        } catch (Exception e) {
            logger.error("<--Redis execption:缓存g更新失败，key:{},异常信息：{}-->",key,e.getMessage());
        }
        return tag;
    }

    /**
     * @title removeKeys
     * @description 批量删除
     * @author miya
     * @params keys
     * @updateTime 2022/12/10 18:42
     */
    public void removeKeys(final String... keys){
        for (String key:keys) {
            removekey(key);
        }
    }

    /**
     * @title removekey
     * @description 删除key
     * @author miya
     * @params key
     * @updateTime 2022/12/10 18:45
     */
    public void removekey(final String key){
        redisTemplate.delete(key);
    }

    /**
     * @title hasKey
     * @description 判断是否有值
     * @author miya
     * @params key
     * @updateTime 2022/12/10 18:47
     */
    public boolean hasKey(final String key){
        Boolean ifExists = redisTemplate.hasKey(key);
        return ifExists.booleanValue();
    }

    /**
     * @title get
     * @description 读取缓存
     * @author miya
     * @params
     * @updateTime 2022/12/10 18:48
     */
    public Object get(final String key) {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        return operations.get(key);
    }

    /**
     * @title hmSet
     * @description 哈希添加
     * @author miya
     * @params
     * @updateTime 2022/12/10 18:49
     */
    public void hmSet(String key, Object hashKey, Object value) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.put(key, hashKey, value);
    }

    /**
     * 哈希获取数据
     */
    public Object hmGet(String key, Object hashKey) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.get(key, hashKey);
    }

    /**
     * @title lPush
     * @description 列表添加
     * @author miya
     * @params
     * @updateTime 2022/12/10 18:49
     */
    public void lPush(String k, Object v) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.rightPush(k, v);
    }

    /**
     * @title lRange
     * @description 列表获取
     * @author miya
     * @params
     * @updateTime 2022/12/10 18:50
     */
    public List<Object> lRange(String k, long l, long l1) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.range(k, l, l1);
    }

    /**
     * @title addSet
     * @description 集合添加
     * @author miya
     * @params
     * @updateTime 2022/12/10 18:51
     */
    public void addSet(String key, Object value) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.add(key, value);
    }

    /**
     * @title removeSetAll
     * @description 删除集合下的所有值
     * @author miya
     * @params
     * @updateTime 2022/12/10 18:52
     */
    public void removeSetAll(String key) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        Set<Object> objectSet = set.members(key);
        if (objectSet != null && !objectSet.isEmpty()) {
            for (Object o : objectSet) {
                set.remove(key, o);
            }
        }
    }

    /**
     * @title isMember
     * @description 判断set集合里面是否包含某个元素
     * @author miya
     * @params
     * @updateTime 2022/12/10 18:52
     */
    public Boolean isMember(String key, Object member) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.isMember(key, member);
    }

    /**
     * @title setMembers
     * @description 集合获取
     * @author miya
     * @params
     * @updateTime 2022/12/10 18:53
     */
    public Set<Object> setMembers(String key) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.members(key);
    }

    /**
     * @title zAdd
     * @description 有序集合添加
     * @author miya
     * @params
     * @updateTime 2022/12/10 18:53
     */
    public void zAdd(String key, Object value, double source) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        zset.add(key, value, source);
    }

    /**
     * @title rangeByScore
     * @description 有序集合获取指定范围的数据
     * @author miya
     * @params
     * @updateTime 2022/12/10 18:54
     */
    public Set<Object> rangeByScore(String key, double source, double source1) {
        ZSetOperations<String, Object> zSet = redisTemplate.opsForZSet();
        return zSet.rangeByScore(key, source, source1);
    }

    /**
     * @title range
     * @description 有序集合升序获取
     * @author miya
     * @params
     * @updateTime 2022/12/10 18:54
     */
    public Set<Object> range(String key, Long source, Long source1) {
        ZSetOperations<String, Object> zSet = redisTemplate.opsForZSet();
        return zSet.range(key, source, source1);
    }

    /**
     * @title reverseRange
     * @description 有序集合降序获取
     * @author miya
     * @params
     * @updateTime 2022/12/10 18:55
     */
    public Set<Object> reverseRange(String key, Long source, Long source1) {
        ZSetOperations<String, Object> zSet = redisTemplate.opsForZSet();
        return zSet.reverseRange(key, source, source1);
    }
}
