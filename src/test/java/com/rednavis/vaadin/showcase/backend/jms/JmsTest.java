package com.rednavis.vaadin.showcase.backend.jms;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.rednavis.vaadin.showcase.jms.MessageReceiver;
import com.rednavis.vaadin.showcase.jms.MessageSender;
import javax.jms.JMSException;
import org.junit.jupiter.api.Test;

class JmsTest {

  private MessageSender sender;
  private MessageReceiver receiver;

  @Test
  public void checkSendersConnection() throws JMSException {
    sender = new MessageSender();
    assertEquals("Client1", sender.getConnection().getClientID());
    sender.getConnection().close();
  }

  @Test
  public void checkMassage() throws JMSException {
    receiver = new MessageReceiver();
    sender = new MessageSender();
    receiver.startListener();
    sender.sendMessage("Hi!");
    assertEquals("Hi!", receiver.getTm().getText());
    sender.destroy();
    receiver.destroy();
  }
}