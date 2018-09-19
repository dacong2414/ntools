package com.nuls.io.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.Callable;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import com.nuls.io.utils.ObjectUtil;

/**
 * 两级缓存，一级:ehcache,二级为redisCache
 * @author yulin
 *
 */
@Data
@Slf4j
public class EhRedisCache implements Cache {
    private static final Logger           log      = LoggerFactory.getLogger(EhRedisCache.class);
    private String                        name;

    private Ehcache                       ehCache;
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    //1 * 60 * 60
    private long                          liveTime = 3600L;                                      //默认1h=1*60*60

    /**
     * 是否重新激活存放在redis中的时间
     */
    private boolean                       KeepLive = true;

    /**
     * 是否激活redis存储服务
     * 默认不激活
     */
    private boolean                       UseRedis = false;

    @Override
    public Object getNativeCache() {
        return this;
    }

    /**
     * 获取缓存中的值
     * @param key 键
     * @return ValueWrapper
     * @see org.springframework.cache.Cache#get(java.lang.Object)
     */
    @Override
    public ValueWrapper get(Object key) {
        Element value = ehCache.get(key);
        log.debug("Cache L1 (ehcache) :{}={}", key, value);
        if (value != null) {
            return new SimpleValueWrapper(value.getObjectValue());
        }
        Object objectValue = null;
        if (UseRedis) {
            objectValue = getFromRedis(key.toString(), KeepLive);
            ehCache.put(new Element(key, objectValue));//取出来之后缓存到本地
            log.debug("Cache L2 (redis) :{}={}", key, objectValue);
        }
        return (objectValue != null ? new SimpleValueWrapper(objectValue) : null);

    }

    /**
     * 获取缓存中的值
     * @param key 键
     * @param <T> 取值
     * @return T
     */
    public <T> T getValue(Object key) {
        ValueWrapper wrapper = get(key);
        if (wrapper == null) {
            return null;
        }
        return ObjectUtil.typeConversion(wrapper.get());
    }

    /**
     * 从redis中获取数据
     * @param keyStr 键
     * @param isKeepLive 是否需要更新存活时间 取默认值 3600m
     * @return T
     */
    public <T> T getFromRedis(final String keyStr, final boolean isKeepLive) {
        return redisTemplate.execute(new RedisCallback<T>() {
            @Override
            public T doInRedis(RedisConnection connection) {
                byte[] key = keyStr.getBytes();
                if (ArrayUtils.isEmpty(key)) {
                    return null;
                }
                byte[] value = connection.get(key);
                if (ArrayUtils.isEmpty(value)) {
                    return null;
                }
                /*
                 * 每次获得，重置缓存过期时间
                 */
                if (isKeepLive && liveTime > 0) {
                    connection.expire(key, liveTime);
                }
                T result = null;
                try {
                    result = ObjectUtil.typeConversion(toObject(value));
                } catch (IOException | ClassNotFoundException e) {
                    log.error(e.getMessage());
                }
                return result;
            }
        }, true);
    }

    /**
     * 存放到缓存中
     * @param key 键
     * @param value 值
     * @see org.springframework.cache.Cache#put(java.lang.Object, java.lang.Object)
     */
    @Override
    public void put(Object key, Object value) {
        ehCache.put(new Element(key, value));
        if (UseRedis) {
            putInRedis(key, value, null);
        }
    }

    public void put(Object key, Object value, Long objectLiveTime) {
        ehCache.put(new Element(key, value));
        if (UseRedis) {
            putInRedis(key, value, objectLiveTime);
        }
    }

    /**
     * 存放到redis中
     * @param key 键
     * @param value 值
     * @param objectLiveTime 存活时间
     */
    public Long putInRedis(final Object key, final Object value, final Long objectLiveTime) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) {
                byte[] finalKey = key.toString().getBytes();
                byte[] finalValue = toByteArray(value);
                connection.set(finalKey, finalValue);
                long tempTime = liveTime;
                if (objectLiveTime != null) {
                    tempTime = objectLiveTime;
                }
                if (tempTime > 0) {
                    connection.expire(finalKey, tempTime);
                }
                return 1L;
            }
        }, true);
    }

    /**
     * 清除对应的缓存
     * @param key 键
     * @see org.springframework.cache.Cache#evict(java.lang.Object)
     */
    @Override
    public void evict(Object key) {
        ehCache.remove(key);
        if (UseRedis) {
            evictRedis(key);
        }
    }

    /**
     * 清除在redis中的缓存
     * @param key 键
     */
    public void evictRedis(Object key) {
        final String keyStr = key.toString();
        redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) {
                return connection.del(keyStr.getBytes());
            }
        }, true);
    }

    /**
     * 清空缓存
     */
    @Override
    public void clear() {
        clearEhcache();
        if (UseRedis) {
            clearRedis();
        }
    }

    /**
     * 清空ehcache
     */
    public void clearEhcache() {
        ehCache.removeAll();
    }

    /**
     * 清空redis
     */
    public void clearRedis() {
        redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) {
                connection.flushDb();
                return "clear done.";
            }
        }, true);
    }

    /** 
     * 描述 : Object转byte[]. <br> 
     * @param obj 对象
     * @return 二进制数据
     */
    private byte[] toByteArray(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
            oos.close();
            bos.close();
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
        return bytes;
    }

    /** 
     * 描述 :  byte[]转Object . <br> 
     * @param bytes 二进制数据
     * @return 实际对象
     */
    private Object toObject(byte[] bytes) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
                ObjectInputStream ois = new ObjectInputStream(bis)) {
            return ois.readObject();
        }
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        return null;
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        return null;
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        return null;
    }

    /** 
     * @return
     * @see org.springframework.cache.Cache#getName()
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Getter method for property <tt>ehCache</tt>.
     * 
     * @return property value of ehCache
     */
    public Ehcache getEhCache() {
        return ehCache;
    }

    /**
     * Setter method for property <tt>ehCache</tt>.
     * 
     * @param ehCache value to be assigned to property ehCache
     */
    public void setEhCache(Ehcache ehCache) {
        this.ehCache = ehCache;
    }

    /**
     * Getter method for property <tt>redisTemplate</tt>.
     * 
     * @return property value of redisTemplate
     */
    public RedisTemplate<Object, Object> getRedisTemplate() {
        return redisTemplate;
    }

    /**
     * Setter method for property <tt>redisTemplate</tt>.
     * 
     * @param redisTemplate value to be assigned to property redisTemplate
     */
    public void setRedisTemplate(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * Getter method for property <tt>liveTime</tt>.
     * 
     * @return property value of liveTime
     */
    public long getLiveTime() {
        return liveTime;
    }

    /**
     * Setter method for property <tt>liveTime</tt>.
     * 
     * @param liveTime value to be assigned to property liveTime
     */
    public void setLiveTime(long liveTime) {
        this.liveTime = liveTime;
    }

    /**
     * Getter method for property <tt>keepLive</tt>.
     * 
     * @return property value of KeepLive
     */
    public boolean isKeepLive() {
        return KeepLive;
    }

    /**
     * Setter method for property <tt>keepLive</tt>.
     * 
     * @param KeepLive value to be assigned to property keepLive
     */
    public void setKeepLive(boolean keepLive) {
        KeepLive = keepLive;
    }

    /**
     * Getter method for property <tt>useRedis</tt>.
     * 
     * @return property value of UseRedis
     */
    public boolean isUseRedis() {
        return UseRedis;
    }

    /**
     * Setter method for property <tt>useRedis</tt>.
     * 
     * @param UseRedis value to be assigned to property useRedis
     */
    public void setUseRedis(boolean useRedis) {
        UseRedis = useRedis;
    }

    /**
     * Setter method for property <tt>name</tt>.
     * 
     * @param name value to be assigned to property name
     */
    public void setName(String name) {
        this.name = name;
    }

}