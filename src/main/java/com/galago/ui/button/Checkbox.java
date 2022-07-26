/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.galago.ui.button;

import com.galago.ui.effect.TouchEffect;
import com.galago.ui.listener.TouchButtonAdapter;
import com.galago.ui.panel.Panel;

/**
 * @author nidebruyn
 */
public class Checkbox extends TouchButton {

  private boolean checked = false;
  private String checkedImage = "Interface/checkbox-checked.png";
  private String uncheckedImage = "Interface/checkbox-unchecked.png";

  public Checkbox(Panel panel, String id) {
    this(panel, id, 48, 48, false);
  }

  public Checkbox(Panel panel, String id, float width, float height, boolean checked) {
    super(panel, id, "Interface/checkbox-unchecked.png", width, height, true);

    if (bitmapText != null) {
      bitmapText.removeFromParent();
    } else if (trueTypeContainer != null) {
      trueTypeContainer.removeFromParent();
    }

    setChecked(checked);

    addEffect(new TouchEffect(this));

    addTouchButtonListener(new TouchButtonAdapter() {

      @Override
      public void doTouchUp(float touchX, float touchY, float tpf, String uid) {
        boolean val = isChecked();
        val = !val;
        setChecked(val);
      }

    });
  }

  public void setChecked(boolean checked) {
    this.checked = checked;

    if (checked) {
      updatePicture(checkedImage);

    } else {
      updatePicture(uncheckedImage);
    }

  }

  public boolean isChecked() {
    return checked;
  }

  public String getCheckedImage() {
    return checkedImage;
  }

  public void setCheckedImage(String checkedImage) {
    this.checkedImage = checkedImage;
  }

  public String getUncheckedImage() {
    return uncheckedImage;
  }

  public void setUncheckedImage(String uncheckedImage) {
    this.uncheckedImage = uncheckedImage;
  }

}
