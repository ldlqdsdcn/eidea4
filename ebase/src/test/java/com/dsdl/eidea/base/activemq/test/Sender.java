package com.dsdl.eidea.base.activemq.test;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

import javax.jms.*;

/**
 * Created by 刘大磊 on 2017/4/24 8:26.
 */
public class Sender {
    public static void main(String[] args)throws Exception{
        ConnectionFactory cf=new ActiveMQConnectionFactory("tcp://192.168.0.57:61616");
        Connection conn=cf.createConnection();
        Session session=conn.createSession(false,Session.AUTO_ACKNOWLEDGE);
        Destination destination=new ActiveMQQueue("spitter.queue");
        MessageProducer producer=session.createProducer(destination);
        TextMessage message=session.createTextMessage();
        message.setText("Hello,Solomon liu 刘大磊是个好人");
        producer.send(message);
    }
}
