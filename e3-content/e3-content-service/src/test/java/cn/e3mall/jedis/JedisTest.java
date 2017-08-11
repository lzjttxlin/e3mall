package cn.e3mall.jedis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

public class JedisTest {
	
	@Test
	public void testJedis() throws Exception{
		Jedis jedis=new Jedis("192.168.25.131", 6379);
		jedis.set("jedisTest", "helloRedis");
		String value = jedis.get("jedisTest");
		System.out.println(value);
		jedis.close();
	}
	
	@SuppressWarnings("resource")
	@Test
	public void testRedisCluster() throws Exception{
		Set<HostAndPort> nodes=new HashSet<>();
		nodes.add(new HostAndPort("192.168.25.131", 7001));
		nodes.add(new HostAndPort("192.168.25.131", 7002));
		nodes.add(new HostAndPort("192.168.25.131", 7003));
		nodes.add(new HostAndPort("192.168.25.131", 7004));
		nodes.add(new HostAndPort("192.168.25.131", 7005));
		nodes.add(new HostAndPort("192.168.25.131", 7006));
		//直接使用JedisCluster管理对象
		JedisCluster jedisCluster = new JedisCluster(nodes);
		jedisCluster.set("testRedis", "hello");
		String value = jedisCluster.get("testRedis");
		System.out.println(value);
		jedisCluster.close();
	}
}
