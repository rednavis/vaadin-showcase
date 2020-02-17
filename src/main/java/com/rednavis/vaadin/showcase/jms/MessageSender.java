package com.rednavis.vaadin.showcase.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageSender {

  private MessageProducer producer;
  private Session session;
  private Connection connection;
  private static final Logger LOGGER = LoggerFactory.getLogger(MessageSender.class);

  public MessageSender() {
    ConnectionFactory factory = JmsProvider.getConnectionFactory();
    try {
    this.connection = factory.createConnection();
    connection.setClientID("Client1");
    connection.start();
    this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    Queue queue = session.createQueue("example.queue");
    producer = session.createProducer(queue);
    } catch (JMSException e) {
      e.printStackTrace();
    }
  }

  public void sendMessage(String message) throws JMSException {
    LOGGER.info("Sending message: " + message);
    TextMessage textMessage = session.createTextMessage(message);
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