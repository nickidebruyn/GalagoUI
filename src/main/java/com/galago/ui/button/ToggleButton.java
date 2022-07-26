/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.galago.ui.button;

import com.galago.ui.panel.Panel;

/**
 * This is a on Off spinner button.
 * When touched or clicked the text swap between "On" and "Off".
 *
 * @author nidebruyn
 */
public class ToggleButton extends TouchButton {

  protected String offImage = "Interface/icon-switch-off.png";
  protected String onImage = "Interface/icon-switch-on.png";
  protected boolean selected = false;

  public ToggleButton(Panel panel, String id) {
    this(panel, id, "Interface/icon-switch-on.png", "Interface/icon-switch-off.png", 60, 35);

  }

  public ToggleButton(Panel panel, String id, String onImage, String offImage, float width, float height) {
    super(panel, id, offImage, width, height, true);
    this.offImage = offImage;
    this.onImage = onImage;

    if (bitmapText != null) {
      bitmapText.removeFromParent();
    } else if (trueTypeContainer != null) {
      trueTypeContainer.removeFromParent();
    }

  }

  @Override
  public void fireTouchUp(float x, float y, float tpf) {

    setSelected(!selected);

    super.fireTouchUp(x, y, tpf);

  }

  /**
   * @param selected
   */
  public void setSelected(boolean selected) {
    if ((this.selected && !selected) || (!this.selected && selected)) {
      this.selected = selected;
      if (selected) {
        updatePicture(onImage);
      } else {
        updatePicture(offImage);
      }
    }

  }

  public boolean isSelected() {
    return selected;
  }

}
