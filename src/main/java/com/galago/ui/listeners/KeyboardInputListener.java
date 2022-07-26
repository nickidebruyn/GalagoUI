package com.galago.ui.listeners;

import com.galago.ui.field.InputType;

import java.util.Properties;

/**
 * @author nidebruyn
 */
public interface KeyboardInputListener {

  /**
   * Send properties to the method and return a string value.
   *
   * @param properties
   * @return
   */
  public String doInput(Properties properties, InputType inputType);

}
