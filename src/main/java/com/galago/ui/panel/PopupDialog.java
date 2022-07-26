/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.galago.ui.panel;

import com.galago.ui.Label;
import com.galago.ui.TextAlign;
import com.galago.ui.window.Window;
import com.jme3.math.ColorRGBA;

/**
 * Extend this class if you wish to create a PopupDialog for yuor game.
 * It already contrains a title widget.
 *
 * @author NideBruyn
 */
public abstract class PopupDialog extends Panel {

  protected Label title;

  /**
   * This is the default popup dialog contrainer.
   *
   * @param window
   */
  public PopupDialog(Window window) {
    this(window, "Interface/panel.png", 600, 400, true);
  }

  public PopupDialog(Window window, String pictureFile, float width, float height) {
    this(window, pictureFile, width, height, true);
  }

  /**
   * @param window
   * @param pictureFile
   * @param width
   * @param height
   */
  public PopupDialog(Window window, String pictureFile, float width, float height, boolean lockScale) {
    super(window, pictureFile, width, height, lockScale);

    title = new Label(this, "Popup", 22, width, 40);
    title.setTextColor(ColorRGBA.DarkGray);
    title.setAlignment(TextAlign.CENTER);
    title.centerTop(0, 10);

    window.add(this);

    center();

  }

  /**
   * @param colorRGBA
   */
  public void setTitleColor(ColorRGBA colorRGBA) {
    title.setTextColor(colorRGBA);
  }

  /**
   * @param size
   */
  public void setTitleSize(float size) {
    //TODO: NEED TO FIX
    title.setFontSize(size);
  }

  /**
   * @param title
   */
  public void setTitle(String title) {
    this.title.setText(title);
  }

}
