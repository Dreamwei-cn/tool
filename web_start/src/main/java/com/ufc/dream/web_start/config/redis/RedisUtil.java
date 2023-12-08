package com.ufc.dream.web_start.config.redis;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.MultiKeyCommands;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @description: redis工具类
 **/
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public boolean expire(String key, long time) {
        try {
            if (time > 0L) {
                this.redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }

            return true;
        } catch (Exception var5) {
            var5.printStackTrace();
            return false;
        }
    }

    public boolean expire(String key, long time,TimeUnit timeUnit) {
        try {
            if (time > 0L) {
                this.redisTemplate.expire(key, time, timeUnit);
            }

            return true;
        } catch (Exception var5) {
            var5.printStackTrace();
            return false;
        }
    }

    public long getExpire(String key) {
        return this.redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    public boolean hasKey(String key) {
        try {
            return this.redisTemplate.hasKey(key);
        } catch (Exception var3) {
            var3.printStackTrace();
            return false;
        }
    }

    /**
     * 根据key删除
     *
     * @param key
     */
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                this.redisTemplate.delete(key[0]);
            } else {
                this.redisTemplate.delete(CollUtil.toList(key));
            }
        }

    }

    /**
     * 模糊删除key
     *
     * @param prex key的前缀
     */
    public void delKeys(String prex) {
        Set<String> keys = redisTemplate.keys(prex);
        if (keys != null && keys.size() > 0) {
            redisTemplate.delete(keys);
        }
    }

    /**
     * 根据key 获取信息
     *
     * @param key key
     * @return Object
     */
    public Object get(String key) {
        return key == null ? null : this.redisTemplate.opsForValue().get(key);
    }

    /**
     * 根据key 获取信息String
     *
     * @param key
     * @return
     */
    public String getString(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        } else {
            Object value = this.redisTemplate.opsForValue().get(key);
            return value == null ? null : String.valueOf(value);
        }
    }

    public boolean set(String key, Object value) {
        try {
            this.redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception var4) {
            var4.printStackTrace();
            return false;
        }
    }

    /**
     * 存储redis
     *
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0L) {
                this.redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                this.set(key, value);
            }

            return true;
        } catch (Exception var6) {
            var6.printStackTrace();
            return false;
        }
    }

    /**
     * 按时存储redis
     *
     * @param key
     * @param value
     * @param time
     * @param timeUnit
     * @return
     */
    public boolean set(String key, Object value, long time, TimeUnit timeUnit) {
        try {
            if (time > 0L) {
                this.redisTemplate.opsForValue().set(key, value, time, timeUnit);
            } else {
                this.set(key, value);
            }

            return true;
        } catch (Exception var7) {
            var7.printStackTrace();
            return false;
        }
    }

    public long incr(String key, long delta) {
        if (delta < 0L) {
            throw new RuntimeException("递增因子必须大于0");
        } else {
            return this.redisTemplate.opsForValue().increment(key, delta);
        }
    }

    /**
     * 自增渠道ID（key-'CH'+tenantId+channelGroupId）
     *
     * @param key
     * @return
     */
    public String incrChannelId(String key) {
        return "CH" + this.redisTemplate.opsForValue().increment(key, 1L).toString() + "-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmss"));
    }

    public long decr(String key, long delta) {
        if (delta < 0L) {
            throw new RuntimeException("递减因子必须大于0");
        } else {
            return this.redisTemplate.opsForValue().increment(key, -delta);
        }
    }

    public Object hget(String key, String item) {
        return this.redisTemplate.opsForHash().get(key, item);
    }

    public Map<Object, Object> hmget(String key) {
        return this.redisTemplate.opsForHash().entries(key);
    }

    public boolean hmset(String key, Map<String, Object> map) {
        try {
            this.redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception var4) {
            var4.printStackTrace();
            return false;
        }
    }

    public boolean hmset(String key, Map<String, Object> map, long time) {
        try {
            this.redisTemplate.opsForHash().putAll(key, map);
            if (time > 0L) {
                this.expire(key, time);
            }

            return true;
        } catch (Exception var6) {
            var6.printStackTrace();
            return false;
        }
    }

    /**
     * hash格式存储
     *
     * @param key
     * @param item
     * @param value
     * @return
     */
    public boolean hset(String key, String item, Object value) {
        try {
            this.redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception var5) {
            var5.printStackTrace();
            return false;
        }
    }

    public boolean hsetAll(String key, Map<Object, Object> map) {
        try {
            this.redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception var4) {
            var4.printStackTrace();
            return false;
        }
    }

    /**
     * hash 定时存储
     *
     * @param key
     * @param item
     * @param value
     * @param time
     * @return
     */
    public boolean hset(String key, String item, Object value, long time) {
        try {
            this.redisTemplate.opsForHash().put(key, item, value);
            if (time > 0L) {
                this.expire(key, time);
            }

            return true;
        } catch (Exception var7) {
            var7.printStackTrace();
            return false;
        }
    }

    /**
     * hash 获取size
     *
     * @param key
     * @return
     */
    public Long hsize(String key) {
        final Long size = this.redisTemplate.opsForHash().size(key);
        return size;
    }

    /**
     * hash 删除
     *
     * @param key
     * @param item
     */
    public void hdel(String key, Object... item) {
        this.redisTemplate.opsForHash().delete(key, item);
    }

    public boolean hHasKey(String key, String item) {
        return this.redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash 自增
     *
     * @param key
     * @param item
     * @param by
     * @return
     */
    public double hincr(String key, String item, double by) {
        return this.redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * hash 自减
     *
     * @param key
     * @param item
     * @param by
     * @return
     */
    public double hdecr(String key, String item, double by) {
        return this.redisTemplate.opsForHash().increment(key, item, -by);
    }

    public Set<Object> sGet(String key) {
        try {
            return this.redisTemplate.opsForSet().members(key);
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public boolean sHasKey(String key, Object value) {
        try {
            return this.redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception var4) {
            var4.printStackTrace();
            return false;
        }
    }

    public long sSet(String key, Object... values) {
        try {
            return this.redisTemplate.opsForSet().add(key, values);
        } catch (Exception var4) {
            var4.printStackTrace();
            return 0L;
        }
    }

    public long sSetAndTime(String key, long time, Object... values) {
        try {
            Long count = this.redisTemplate.opsForSet().add(key, values);
            if (time > 0L) {
                this.expire(key, time);
            }

            return count;
        } catch (Exception var6) {
            var6.printStackTrace();
            return 0L;
        }
    }

    public long sGetSetSize(String key) {
        try {
            return this.redisTemplate.opsForSet().size(key);
        } catch (Exception var3) {
            var3.printStackTrace();
            return 0L;
        }
    }

    public long setRemove(String key, Object... values) {
        try {
            Long count = this.redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception var4) {
            var4.printStackTrace();
            return 0L;
        }
    }

    public List<Object> lGet(String key, long start, long end) {
        try {
            return this.redisTemplate.opsForList().range(key, start, end);
        } catch (Exception var7) {
            var7.printStackTrace();
            return null;
        }
    }

    public long lGetListSize(String key) {
        try {
            return this.redisTemplate.opsForList().size(key);
        } catch (Exception var3) {
            var3.printStackTrace();
            return 0L;
        }
    }

    public Object lGetIndex(String key, long index) {
        try {
            return this.redisTemplate.opsForList().index(key, index);
        } catch (Exception var5) {
            var5.printStackTrace();
            return null;
        }
    }

    public boolean lSet(String key, Object value) {
        try {
            this.redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception var4) {
            var4.printStackTrace();
            return false;
        }
    }

    public boolean lSet(String key, Object value, long time) {
        try {
            this.redisTemplate.opsForList().rightPush(key, value);
            if (time > 0L) {
                this.expire(key, time);
            }

            return true;
        } catch (Exception var6) {
            var6.printStackTrace();
            return false;
        }
    }

    public boolean lSet(String key, List<Object> value) {
        try {
            this.redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception var4) {
            var4.printStackTrace();
            return false;
        }
    }

    public boolean lSet(String key, List<Object> value, long time) {
        try {
            this.redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0L) {
                this.expire(key, time);
            }

            return true;
        } catch (Exception var6) {
            var6.printStackTrace();
            return false;
        }
    }

    public boolean lUpdateIndex(String key, long index, Object value) {
        try {
            this.redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception var6) {
            var6.printStackTrace();
            return false;
        }
    }

    public long lRemove(String key, long count, Object value) {
        try {
            Long remove = this.redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception var6) {
            var6.printStackTrace();
            return 0L;
        }
    }

    /**
     * 从指定队列里取得数据
     *
     * @param key
     * @param size
     * @return
     */
    public List<Object> getFromQueue(String key, long size) {
        ListOperations<String, Object> stringObjectListOperations = redisTemplate.opsForList();
        if (size > 0) {
            return stringObjectListOperations.range(key, 0, size - 1);
        } else {
            return stringObjectListOperations.range(key, 0, stringObjectListOperations.size(key) - 1);
        }
    }

    /**
     * 存到指定的队列中
     *
     * @param key
     * @param val
     * @param size
     */
    public Long saveToQueue(String key, String val, long size) {

        ListOperations<String, Object> lo = redisTemplate.opsForList();

        if (size > 0 && lo.size(key) >= size) {
            lo.rightPop(key);
        }
       return lo.leftPush(key, val);
    }

    /**
     * 获取hash表中全部K-V数据
     *
     * @param key
     */
    public Map<Object, Object> hgetAll(String key) {
        Map<Object, Object> result = redisTemplate.opsForHash().entries(key);
        return result;
    }

    public Set<String> keys(String keyprefix) {
        Set<String> keySet = redisTemplate.keys(keyprefix + "*");
        return keySet;
    }

    public Set<String> scan(String matchKey) {
        Set<String> keys = redisTemplate.execute((RedisCallback<Set<String>>) connection -> {
            Set<String> keysTmp = new HashSet<>();
            Cursor<byte[]> cursor = connection.scan(ScanOptions.scanOptions().match(matchKey + "*").count(100000).build());
            while (cursor.hasNext()) {
                keysTmp.add(new String(cursor.next()));
            }
            return keysTmp;
        });

        return keys;
    }

    /**
     *
     * @param key scan 操作key
     * @return set
     */
    public Set<String> scan1(String key) {
        return redisTemplate.execute((RedisCallback<Set<String>>) connection -> {
            Set<String> keys = new HashSet<>();

            JedisCommands commands = (JedisCommands) connection.getNativeConnection();
            MultiKeyCommands multiKeyCommands = (MultiKeyCommands) commands;

            ScanParams scanParams = new ScanParams();
            scanParams.match(key);
            scanParams.count(1000);
            ScanResult<String> scan = multiKeyCommands.scan("0", scanParams);
            while (null != scan.getStringCursor()) {
                keys.addAll(scan.getResult());
                if (!StringUtils.equals("0", scan.getStringCursor())) {
                    scan = multiKeyCommands.scan(scan.getStringCursor(), scanParams);
                    continue;
                } else {
                    break;
                }
            }
            return keys;
        });
    }

    /**
     * 不设置过期时长
     */
    private final static long NOT_EXPIRE = -1;

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public <T> T getCacheObject(final String key, Class<T> clazz) {
        ValueOperations<String, Object> operation = redisTemplate.opsForValue();
        T t = (T) operation.get(key);
        if (t == null) {
            return null;
        }
        if (clazz == String.class) {
            return t;
        }
        return fromJson(t.toString(), clazz);
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key   缓存的键值
     * @param value 缓存的值
     */
    public <T> void setCacheObject(final String key, final T value) {
        redisTemplate.opsForValue().set(key, toJson(value));
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key      缓存的键值
     * @param value    缓存的值
     * @param timeout  时间
     * @param timeUnit 时间颗粒度
     */
    public <T> void setCacheObject(final String key, final T value, final Integer timeout, final TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, toJson(value), timeout, timeUnit);
    }

    /**
     * Object转成JSON数据
     */
    private String toJson(Object object) {
        if (object instanceof Integer || object instanceof Long || object instanceof Float || object instanceof Double
                || object instanceof Boolean || object instanceof String) {
            return String.valueOf(object);
        }

        return JSON.toJSONString(object);

    }

    /**
     * JSON数据，转成Object
     */
    private <T> T fromJson(String json, Class<T> clazz) {
        return JSONObject.parseObject(json,clazz);
    }

    /**
     * 删除集合对象
     *
     * @param collection 多个对象
     * @return
     */
    public long deleteObject(final Collection collection) {
        return redisTemplate.delete(collection);
    }

    public RedisUtil(final RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}