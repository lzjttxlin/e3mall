package cn.e3mall.activiemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

public class ActiveMqTest {
	//消息的生产者
	@Test
	public void queueProducer() throws Exception{
		ConnectionFactory connectionFactory=new ActiveMQConnectionFactory("tcp://192.168.25.135:61616");
		//使用connectionFactory创建一个connection对象
		Connection connection = connectionFactory.createConnection();
		//开启连接
		connection.start();
		//获得session
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//使用session创建Destination对象
		Queue queue = session.createQueue("test-queue");
		//使用session创建一个producer对象
		MessageProducer createProducer = session.createProducer(queue);
		//使用session创建一个TestMessage对象
		TextMessage textMessage = session.createTextMessage("hello activeMq");
		//发送消息
		createProducer.send(textMessage);
		//关闭资源
		createProducer.close();
		session.close();
		connection.close();
	}
	@Test
	//消息的消费者
	public void queueConsumer() throws Exception{
		ConnectionFactory connectionFactory=new ActiveMQConnectionFactory("tcp://192.168.25.135:61616");
		//创建连接
		Connection connection = connectionFactory.createConnection();
		//开启连接
		connection.start();
		//获得session对象
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//创建一个queue对象
		Queue queue = session.createQueue("test-queue");
		//创建一个消息的消费者
		MessageConsumer createConsumer = session.createConsumer(queue);
		//设置消息的监听对象
		createConsumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				
				try {
					//接收消息并打印消息
					TextMessage textMessage=(TextMessage) message;
					System.out.println(textMessage.getText());
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		//等待消息的启动
		System.in.read();
		//关闭资源
		createConsumer.close();
		session.close();
		connection.close();
	}
	
	@Test
	public void topicProducer() throws Exception{
		ConnectionFactory connectionFactory=new ActiveMQConnectionFactory("tcp://192.168.25.135:61616");
		//使用connectionFactory创建一个connection对象
		Connection connection = connectionFactory.createConnection();
		//开启连接
		connection.start();
		//获得session
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//使用session创建Destination对象
		//Queue queue = session.createQueue("");
		Topic topic = session.createTopic("test-topic");
		//使用session创建一个prodtest-queueucer对象
		MessageProducer createProducer = session.createProducer(topic);
		//使用session创建一个TestMessage对象
		TextMessage textMessage = session.createTextMessage("hello activeMq11111");
		//发送消息
		createProducer.send(textMessage);
		//关闭资源
		createProducer.close();
		session.close();
		connection.close();
	}
	@Test
	//消息的消费者
	public void topicConsumer() throws Exception{
		ConnectionFactory connectionFactory=new ActiveMQConnectionFactory("tcp://192.168.25.135:61616");
		//创建连接
		Connection connection = connectionFactory.createConnection();
		//开启连接
		connection.start();
		//获得session对象
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//创建一个queue对象
		//Queue queue = session.createQueue("test-queue");
		Topic topic = session.createTopic("test-topic");
		//创建一个消息的消费者
		//MessageConsumer createConsumer = session.createConsumer(topic);
		TopicSubscriber createConsumer = session.createDurableSubscriber(topic, "meimei");
		//设置消息的监听对象
		createConsumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				
				try {
					//接收消息并打印消息
					TextMessage textMessage=(TextMessage) message;
					System.out.println(textMessage.getText());
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		//等待消息的启动
		System.in.read();
		//关闭资源
		createConsumer.close();
		session.close();
		connection.close();
	}
}
