package redis;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisCommands;

import java.util.concurrent.TimeUnit;


/**
 * Redis锁实现
 * <p/>
 * Created by quanjic on 2017/7/11.
 */
public class RedisDistributedLock {

    private static final Logger logger = LoggerFactory.getLogger(RedisDistributedLock.class);

    private String key;

    private JedisCommands commands;

    /**
     * 有效时间
     */
    private long validTimeMill;

    /**
     * @param key       锁唯一键
     * @param commands
     * @param validTime 锁的有效时间(大于0)(尽量长)。
     * @param timeUnit  时间单位
     */
    public RedisDistributedLock(String key, JedisCommands commands, long validTime, TimeUnit timeUnit) {
        if (StringUtils.isEmpty(key)) {
            throw new NullPointerException();
        }
        if (commands == null || timeUnit == null) {
            throw new NullPointerException();
        }
        if (validTime <= 0) {
            throw new IllegalArgumentException("validTimeMill <= 0");
        }
        this.key = key;
        this.commands = commands;
        this.validTimeMill = timeUnit.toMillis(validTime);
    }

    private volatile byte flag = 0;

    private volatile long lockExpireTime;

    /**
     *
     * @return
     * @throw IllegalMonitorStateException 如果锁已经存在
     */

    public  synchronized boolean tryLock() throws IllegalMonitorStateException {

        if(flag == 1) {
            throw new IllegalMonitorStateException();
        }

        long et = System.currentTimeMillis() + validTimeMill;
        String ok = commands.set(key, String.valueOf(et), "NX", "PX", validTimeMill);
        if ("OK".equals(ok)) {
            lockExpireTime = et;
            flag = 1;
            return true;
        }

        String v2 = commands.get(key);
        if (v2 != null && Long.parseLong(v2) >= System.currentTimeMillis()) { //未过期
            return false;
        }

        et = System.currentTimeMillis() + validTimeMill;
        String v3 = commands.getSet(key, String.valueOf(et));

        if (v2 == null) {
            if (v3 != null) {
                return false;
            }
        } else if (v3 != null && !v2.equals(v3)) {
            return false;
        }

        lockExpireTime = et;
        flag = 1;
        commands.pexpire(key, validTimeMill);

        return true;
    }


    /**
     * 释放锁
     *
     * @throws IllegalMonitorStateException 如果当前节点没有持有该锁或者已经超时将抛出此异常
     */
    public synchronized void unlock() throws IllegalMonitorStateException {
        if (flag != 1) {
            throw new IllegalMonitorStateException();
        }
        if (lockExpireTime >= System.currentTimeMillis()) {
            commands.del(key);
        } else {
            logger.warn("#DistributedLock#unlock del key({}) fail: timeout expired!", key);
            throw new IllegalMonitorStateException("timeout");
        }
        flag = 0;
    }

}
