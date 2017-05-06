package com.dsdl.eidea.base.activemq.test;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

import javax.jms.*;

/**
 * Created by 刘大磊 on 2017/4/24 8:35.
 */
public class Receiver {
    public static void main(String[] args)throws  Exception
    {
        ConnectionFactory cf=new ActiveMQConnectionFactory("tcp://192.168.0.57:61616");
        Connection conn=cf.createConnection();
        conn.start();
        Session session=conn.createSession(false,Session.AUTO_ACKNOWLEDGE);
        Destination destination=new ActiveMQQueue("spitter.queue");
        MessageConsumer consumer=session.createConsumer(destination);
        Message message=consumer.receive();
        TextMessage textMessage=(TextMessage)message;
        System.out.println("GOT A　MESSAGE:"+textMessage.getText());
        conn.start();
    }
}
