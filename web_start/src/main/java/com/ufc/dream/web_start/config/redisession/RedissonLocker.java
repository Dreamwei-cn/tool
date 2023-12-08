package com.ufc.dream.web_start.config.redisession;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * @program: taidu8
 * @description:
 * @author: superfish
 * @create: 2020-08-10 11:41
 **/
public class RedissonLocker implements Locker {

    private RedissonClient redissonClient;

    public RedissonLocker(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    /**
     * 没有超时时间,默认30s
     *
     * @param lockKey
     * @return
     */

    @Override
    public void lock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock();
    }

    @Override
    public void unlock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            lock.unlock();
        } catch (Exception e) {
            if (lock.isLocked()) {
                if (lock.isHeldByCurrentThread()) {
                    lock.unlock();
                }
            }
        }
    }

    /**
     * 自己设置超时时间
     *
     * @param lockKey   锁的key
     * @param leaseTime 秒  如果是-1，直到自己解锁，否则不会自动解锁
     * @return
     */

    @Override
    public void lock(String lockKey, int leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(leaseTime, TimeUnit.SECONDS);
    }

    @Override
    public void lock(String lockKey, TimeUnit unit, int timeout) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeout, unit);
    }

    public void setRedissonClient(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public boolean tryLock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        return lock.tryLock();
    }

    /**
     * @param lockKey   锁key
     * @param waitTime  等到最大时间，强制获取锁
     * @param leaseTime 锁失效时间
     * @param unit      锁单位
     * @return
     * @throws InterruptedException
     */
    @Override
    public boolean tryLock(String lockKey, long waitTime, long leaseTime,
                           TimeUnit unit) throws InterruptedException {
        RLock lock = redissonClient.getLock(lockKey);
        return lock.tryLock(waitTime, leaseTime, unit);
    }

    @Override
    public boolean isLocked(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        return lock.isLocked();
    }
}