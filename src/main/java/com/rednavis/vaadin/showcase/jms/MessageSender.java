package com.rednavis.vaadin.showcase.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

public class MessageSender {

  private final MessageProducer producer;
  private final Session session;
  private final Connection connection;

  public MessageSender() throws JMSException {
    ConnectionFactory factory = JmsProvider.getConnectionFactory();
    this.connection = factory.createConnection();
    connection.setClientID("Client1");
    connection.start();
    this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    Queue queue = session.createQueue("example.queue");

    producer = session.createProducer(queue);
  }

  public void sendMessage(String message) throws JMSException {
    System.out.printf("Sending message: %s, Thread:%s%n",
        message,
        Thread.currentThread().getName());
    TextMessage textMessage = session.createTextMessage(message);
//     JMS features
//    producer.setPriority(1);
//    producer.setDeliveryDelay(3000);
//    producer.setTimeToLive(3000);
    producer.send(textMessage);
  }

  public void destroy() throws JMSException {
    connection.close();
  }

  public Connection getConnection() {
    return connection;
  }

  public Session getSession() {
    return session;
  }
}