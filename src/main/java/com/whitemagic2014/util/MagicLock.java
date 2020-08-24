package com.whitemagic2014.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description: string-> 对象 获得锁对象
 * @author: magic chen
 * @date: 2020/8/22 22:33
 **/
public class MagicLock {

    private static Map<String, Object> forLock = new ConcurrentHashMap<String, Object>();
    private static Map<String, Object> forPrivateLock = new ConcurrentHashMap<String, Object>();
    private static Map<String, ReentrantLock> forLockRemoveable = new ConcurrentHashMap<String, ReentrantLock>();


    /**
     * @Name: getLock
     * @Description: 获得锁对象 配合 synchronized 使用, 这个锁有缺陷，不能释放锁占用的内存
     * @Param: lockKey
     * @Return: java.lang.Object
     * @Author: magic chen
     * @Date: 2020/8/22 22:35
     **/
    public static Object getLock(String lockKey) {
        Object lock = forLock.putIfAbsent(lockKey, new Object());
        if (null == lock) {
            lock = forLock.get(lockKey);
        }
        return lock;
    }

    //##### 一个很酷炫的 锁 ####


    /**
     * @Name: getPrivateLock
     * @Description: 拿锁 ：为每一个线程提供不同的 唯一锁
     * @Param: lockKey
     * @Return: java.lang.Object
     * @Author: magic chen
     * @Date: 2020/8/22 22:35
     **/
    public static Object getPrivateLock(String lockKey) {
        return new Object();
    }


    /**
     * @Name: waitLock
     * @Description: 等锁 ：如果map中有其他锁存在，则表明有其他线程在锁这个key，等其他线程的锁被释放，将自己的锁放入map
     * @Param: lockKey
     * @Param: lock
     * @Return: void
     * @Author: magic chen
     * @Date: 2020/8/22 22:35
     **/
    public static void waitLock(String lockKey, Object lock) {
        Object oldLock = null;
        while ((oldLock = forPrivateLock.putIfAbsent(lockKey, lock)) != null) {
            synchronized (oldLock) {
                // nothing to do,but need;
            }
        }
    }


    /**
     * @Name: removePrivateLock
     * @Description: 释放锁
     * @Param: lockKey
     * @Param: object
     * @Return: void
     * @Author: magic chen
     * @Date: 2020/8/22 22:36
     **/
    public static void removePrivateLock(String lockKey, Object object) {
        forPrivateLock.remove(lockKey, object);
    }

    /**
     * @Name: busniessSimulator
     * @Description: 酷炫锁使用方法业务模板
     * @Param:
     * @Return: void
     * @Author: magic chen
     * @Date: 2020/8/22 22:36
     **/
    private void busniessSimulator() {
        String lockKey = "";//先搞到key
        Object lock = MagicLock.getPrivateLock(lockKey);// 获得 属于本线程 的唯一锁

        MagicLock.waitLock(lockKey, lock);//等锁
        synchronized (lock) {
            try {
                // 业务
            } catch (Exception e) {
                // 如果业务中用了事务，为了让事务生效，则要抛出RuntimeException
                e.printStackTrace();
                throw new RuntimeException();
            } finally {
                MagicLock.removePrivateLock(lockKey, lock);
            }
        }
    }
    //#####  酷炫锁结束 ####


    // ######  公平重入锁 #####

    /**
     * @Name: getLockRemoveable
     * @Description: 获得Removeable锁对象 单独使用 记得try - finally unlock
     * @Param: lockKey
     * @Return: 一个公平的重入锁 ReentrantLock
     * @Author: magic chen
     * @Date: 2020/8/22 22:37
     **/
    public static ReentrantLock getLockRemoveable(String lockKey) {
        ReentrantLock lock = forLockRemoveable.putIfAbsent(lockKey, new ReentrantLock(true));
        if (null == lock) {
            lock = forLockRemoveable.get(lockKey);
        }
        return lock;
    }


    /**
     * @Name: remove
     * @Description: 释放Removeable锁占用的内存
     * @Param: lockkey
     * @Author: magic chen
     * @Date: 2020/8/22 22:38
     **/
    public static void remove(String lockkey) {
        if (forLockRemoveable.containsKey(lockkey) && !forLockRemoveable.get(lockkey).hasQueuedThreads()
                && !forLockRemoveable.get(lockkey).isLocked()) {
            forLockRemoveable.remove(lockkey);
        }
    }

    /**
     * @Name: clear
     * @Description: 对所有的Removeable 锁对象清理
     * @Author: magic chen
     * @Date: 2020/8/22 22:38
     **/
    public static void clear() {
        for (String lockkey : forLockRemoveable.keySet()) {
            if (!forLockRemoveable.get(lockkey).hasQueuedThreads() && !forLockRemoveable.get(lockkey).isLocked()) {
                forLockRemoveable.remove(lockkey);
            }
        }
    }

    // ######  公平重入锁 结束 #####

}
