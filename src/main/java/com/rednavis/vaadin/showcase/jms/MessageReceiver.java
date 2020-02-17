package com.rednavis.vaadin.showcase.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageReceiver implements MessageListener {

  private Connection connection;
  private TextMessage tm;
  private static final Logger LOGGER = LoggerFactory.getLogger(MessageReceiver.class);

  public void startListener() throws JMSException {
    ConnectionFactory factory = JmsProvider.getConnectionFactory();
    this.connection = factory.createConnection();
    connection.start();
    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    Queue queue = session.createQueue("example.queue");
    MessageConsumer consumer = session.createConsumer(queue);
    consumer.setMessageListener(this);
  }

  @Override
  public void onMessage(Message message) {
    if (message instanceof TextMessage) {
      tm = (TextMessage) message;
      try {
        LOGGER.info("Message received: " + tm.getText());
      } catch (JMSException e) {
        e.printStackTrace();
      }
    }
  }

  public void destroy() throws JMSException {
    connection.close();
  }

  public TextMessage getTm() {
    return tm;
  }
}