package redis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.Set;

/**
 * Created by liangl on 2019/10/18.
 */
public class TestRedis {

    //批量写redis
    public static void main(String[] args) {

        JedisCluster jedisCluster = new JedisCluster((Set<HostAndPort>) new Jedis("192",111));
        JedisClusterPipeline jcp = JedisClusterPipeline.pipelined(jedisCluster);
        try {
            jcp.refreshCluster();
            for (int i = 0; i < 10000; i++) {
                jcp.setnx("key"+i, "0");
            }
            jcp.sync();
        } catch (Exception e) {

        } finally {
            jcp.close();
        }
    }
}
