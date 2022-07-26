package com.galago.ui.listeners;

import java.util.Properties;

/**
 * @author nidebruyn
 */
public interface RemoteActionListener {

  /**
   * Send properties to the method and return a string value.
   *
   * @param properties
   * @return
   */
  public String doAction(Properties properties);

}
