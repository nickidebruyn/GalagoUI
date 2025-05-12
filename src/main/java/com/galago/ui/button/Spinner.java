/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.galago.ui.button;

import com.galago.ui.panel.Panel;

/**
 * A spinner widget is a button the loop over an array of values that is set to it.
 * This can be used for on/off switches.
 *
 * @author nidebruyn
 */
public class Spinner extends TouchButton {

  private String[] options = null;
  private int index = 0;

  /**
   * @param panel
   * @param id
   * @param options
   */
  public Spinner(Panel panel, String id, String[] options) {
    super(panel, id, " ");
    this.options = options;
    this.index = 0;

    setFontSize(18);
    refreshOptions();
  }

  /**
   * @param panel
   * @param id
   * @param pictureFile
   * @param width
   * @param height
   * @param options
   */
  public Spinner(Panel panel, String id, String pictureFile, float width, float height, String[] options) {
    super(panel, id, pictureFile, width, height);
    this.options = options;
    this.index = 0;

    setFontSize(18);
    refreshOptions();

  }

  protected void refreshOptions() {
    if (options != null && options.length > 0 && index < options.length) {
      String text = options[index];
      setText(text);
    } else {
      setText(" ");
    }
  }

  @Override
  public void fireTouchUp(float x, float y, float tpf) {
//        System.out.println("x="+x+";y="+y);
//        System.out.println("wx="+widgetNode.getWorldTranslation().x+";y="+widgetNode.getWorldTranslation().y);

    //Only change the options if mouse pressed actions occurred on the left or right side
    if (!(x == 0 && y == 0)) {
      int dir = 1;
      if (x < widgetNode.getWorldTranslation().x) {
        dir = -1;
      }

      updateSelection(dir);
    }
    super.fireTouchUp(x, y, tpf);

  }

  private void updateSelection(int dir) {
    if (options != null && options.length > 0) {
      index += dir;
      if (index >= options.length) {
        index = 0;
      } else if (index < 0) {
        index = options.length-1;
      }

    }
    refreshOptions();
  }

  public void spinUp() {
    updateSelection(1);
    super.fireTouchUp(0, 0, 0);
  }

  public void spinDown() {
    updateSelection(-1);
    super.fireTouchUp(0, 0, 0);
  }

  public String[] getOptions() {
    return options;
  }

  public int getIndex() {
    return index;
  }

  /**
   * @param index
   */
  public void setSelection(int index) {
    this.index = index;
    refreshOptions();
  }

  /**
   * @param options
   */
  public void setOptions(String[] options) {
    this.options = options;
    setSelection(0);
    refreshOptions();
  }

}
