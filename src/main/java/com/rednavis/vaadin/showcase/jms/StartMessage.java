package com.rednavis.vaadin.showcase.jms;

public class StartMessage {

  public static void main(String[] args) throws Exception {
    final MessageSender sender = new MessageSender();
    final MessageReceiver receiver = new MessageReceiver();
    receiver.startListener();
    for (int i = 1; i <= 5; i++) {
      String m = "Massage " + i;
      sender.sendMessage(m);
    }
    sender.destroy();
    receiver.destroy();
  }
}
