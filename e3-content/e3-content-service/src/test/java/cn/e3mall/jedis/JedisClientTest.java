package cn.e3mall.jedis;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.e3mall.common.jedis.JedisClient;
import redis.clients.jedis.JedisCluster;

public class JedisClientTest {
	
	@Test
	public void testJedisClient() throws Exception{
		ApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
		JedisCluster jedisCluster = applicationContext.getBean(JedisCluster.class);
		String value = jedisCluster.get("str");
		System.out.println(value);
	}
}
