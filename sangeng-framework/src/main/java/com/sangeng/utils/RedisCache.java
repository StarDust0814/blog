package com.sangeng.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisCache {
    @Autowired
    private RedisTemplate redisTemplate;

    public <T> void setCacheObject(String key, T value){
        redisTemplate.opsForValue().set(key, value);
    }

    public <T> void setCacheObject(String key, T value, final long timeout, final TimeUnit timeUnit){
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    public boolean expire(final String key, final long timeout, TimeUnit seconds){
        return expire(key,timeout,seconds);
    }

    public boolean expire(final String key, final long timeout){
        return expire(key,timeout,TimeUnit.SECONDS);
    }

    public <T> T getCacheObject(final String key){
        ValueOperations<String ,T> op = redisTemplate.opsForValue();
        return op.get(key);
    }

    public boolean deleteObject(final String key){
        return redisTemplate.delete(key);
    }
    public long deleteObject(final Collection collection){
        return redisTemplate.delete(collection);
    }
    public <T> long setCacheList(final String key, final List<T> dataList){
        Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
        return count == null ? 0 :count;
    }


    public boolean hasKey(final String key) {
        return redisTemplate.hasKey(key);
    }

    public Set<String> getKeys(final String pattern) {
        return redisTemplate.keys(pattern);
    }

    public <T> void hashPut(final String key, final String hashKey, final T value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    public <T> void hashPutAll(final String key, final Map<String, T> hash) {
        redisTemplate.opsForHash().putAll(key, hash);
    }

    public <T> T hashGet(final String key, final String hashKey) {
        return (T) redisTemplate.opsForHash().get(key, hashKey);
    }

    public <T> Map<String, T> hashGetAll(final String key) {
        return (Map<String, T>) redisTemplate.opsForHash().entries(key);
    }

    public void hashDelete(final String key, final Object... hashKeys) {
        redisTemplate.opsForHash().delete(key, hashKeys);
    }

    public boolean hashHasKey(final String key, final String hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    public long hashSize(final String key) {
        return redisTemplate.opsForHash().size(key);
    }

    public long listLeftPush(final String key, final Object... values) {
        return redisTemplate.opsForList().leftPushAll(key, values);
    }

    public long listRightPush(final String key, final Object... values) {
        return redisTemplate.opsForList().rightPushAll(key, values);
    }

    public <T> List<T> listRange(final String key, final long start, final long end) {
        return (List<T>) redisTemplate.opsForList().range(key, start, end);
    }

    public long listSize(final String key) {
        return redisTemplate.opsForList().size(key);
    }

    public long listRemove(final String key, final long count, final Object value) {
        return redisTemplate.opsForList().remove(key, count, value);
    }

    public <T> void setAdd(final String key, final T... values) {
        redisTemplate.opsForSet().add(key, values);
    }

    public <T> Set<T> setMembers(final String key) {
        return (Set<T>) redisTemplate.opsForSet().members(key);
    }

    public long setSize(final String key) {
        return redisTemplate.opsForSet().size(key);
    }

    public boolean setIsMember(final String key, final Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    public long setRemove(final String key, final Object... values) {
        return redisTemplate.opsForSet().remove(key, values);
    }

    public double increment(final String key, final double delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    public double decrement(final String key, final double delta) {
        return redisTemplate.opsForValue().increment(key, -delta);
    }
}
