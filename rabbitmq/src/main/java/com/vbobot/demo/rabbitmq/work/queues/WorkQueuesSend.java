package com.vbobot.demo.rabbitmq.work.queues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.nio.charset.StandardCharsets;

/**
 * @author Bobo
 * @date 2021/6/22
 */
public class WorkQueuesSend {
    private final static String QUEUE_NAME = "work_queues";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("120.76.201.127");
        factory.setUsername("rabbitmq");
        factory.setPassword("123456");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
//            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            StringBuilder points = new StringBuilder();
            for (int i=0; i<10; i++) {
                points.append(".");
                final String msg = "Message [" + i + "]" + points;
                channel.basicPublish("", QUEUE_NAME,
                        MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes(StandardCharsets.UTF_8));
                System.out.println(" [x] Sent '" + msg + "'");
            }
        }
    }
}
