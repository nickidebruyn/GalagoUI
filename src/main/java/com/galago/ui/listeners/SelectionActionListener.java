package com.galago.ui.listeners;

import java.util.HashMap;

/**
 * @author nidebruyn
 */
public interface SelectionActionListener {

  /**
   * Send properties to the method and return a string value.
   *
   * @return
   */
  public void doSelectionOption(HashMap<Integer, String> items);

}
