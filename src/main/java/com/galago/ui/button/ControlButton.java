/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.galago.ui.button;

import com.galago.ui.panel.Panel;

/**
 * This is an invisible button on the screen. It can be used to do screen touch
 * controls such as swipe left or swipe right, etc.
 *
 * @author NideBruyn
 */
public class ControlButton extends TouchButton {

  public ControlButton(Panel panel, String uid, float width, float height) {
    super(panel, uid, "Interface/blank.png", width, height);

  }

  public ControlButton(Panel panel, String uid, float width, float height, boolean lockScale) {
    super(panel, uid, "Interface/blank.png", width, height, lockScale);

  }

  public ControlButton(Panel panel, String uid, String image, float width, float height) {
    super(panel, uid, image, width, height, true);

  }

  public ControlButton(Panel panel, String uid, String image, float width, float height, boolean lockScale) {
    super(panel, uid, image, width, height, lockScale);

  }
}
