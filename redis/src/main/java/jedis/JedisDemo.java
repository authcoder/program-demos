package jedis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.Set;

public class JedisDemo {
    Jedis jedis = new Jedis("192.168.132.129", 6379);

    /**
     * 测试连接 Redis
     */
    @Test
    public void connectRedis() {
//        Jedis jedis = new Jedis("192.168.132.129", 6379);
        String ping = jedis.ping();
        System.out.println("连接成功：" + ping);
//        jedis.close();
    }

    /**
     * key
     */
    @Test
    public void key() {
        jedis.set("k1", "v1");
        jedis.set("k2", "v2");
        jedis.set("k3", "v3");

        Set<String> keys = jedis.keys("*");
        System.out.println(keys);

        System.out.println(jedis.exists("k1"));
        System.out.println(jedis.ttl("k1"));
        System.out.println(jedis.get("k1"));
    }

    @Test
    public void string() {
        jedis.mset("str1", "v1","str2","v2");
        System.out.println(jedis.mget("str1","str2"));
    }

}
