package com.galago.ui.managers;

import com.galago.ui.app.GalagoApplication;
import com.galago.ui.listeners.MessageListener;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * The message manager can be used to pass messages between classes in the system.
 *
 * @author nidebruyn
 */
public class MessageManager {

  private GalagoApplication application;
  private ArrayList<MessageListener> messageListeners = new ArrayList<MessageListener>();

  public MessageManager(GalagoApplication galagoApplication) {
    this.application = galagoApplication;
  }

  public void destroy() {
    messageListeners.clear();
  }

  public void clear() {
    messageListeners.clear();
  }

  public void addMessageListener(MessageListener messageListener) {
    this.messageListeners.add(messageListener);
  }

  public void removeMessageListener(MessageListener messageListener) {
    this.messageListeners.remove(messageListener);
  }

  public void sendMessage(String message, Object object) {
    for (Iterator<MessageListener> it = messageListeners.iterator(); it.hasNext(); ) {
      MessageListener messageListener = it.next();
      messageListener.messageReceived(message, object);
    }
  }

}
