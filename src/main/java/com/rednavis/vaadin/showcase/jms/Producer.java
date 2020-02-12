package com.rednavis.vaadin.showcase.jms;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.jms.Topic;

public class Producer {

  @Resource(lookup = "java:comp/DefaultJMSConnectionFactory")
  private static ConnectionFactory connectionFactory;
  @Resource(lookup = "jms/MyQueue")
  private static Queue queue;
  @Resource(lookup = "jms/MyTopic")
  private static Topic topic;
}
