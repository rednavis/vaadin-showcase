package com.rednavis.vaadin.showcase.jms;

import javax.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;

public class JmsProvider {

  public static ConnectionFactory getConnectionFactory() {
    ConnectionFactory connectionFactory =
        new ActiveMQConnectionFactory("vm://localhost");
    return connectionFactory;
  }
}