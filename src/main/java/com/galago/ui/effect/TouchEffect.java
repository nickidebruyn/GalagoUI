/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.galago.ui.effect;

import com.galago.ui.Widget;

/**
 * This effect can be added to a TouchButton.
 * When the button is clicked it will scale up and down.
 *
 * @author NideBruyn
 */
public class TouchEffect extends Effect {

  /**
   * @param widget
   */
  public TouchEffect(Widget widget) {
    super(widget);
  }

  @Override
  protected void doShow() {

  }

  @Override
  protected void doHide() {

  }

  @Override
  protected void doTouchDown() {
    widget.setScale(0.96f);
  }

  @Override
  protected void doTouchUp() {
    widget.setScale(1f);
  }

  @Override
  protected void doEnabled(boolean enabled) {

  }

  @Override
  protected void controlUpdate(float tpf) {

  }

  @Override
  protected void doSelected() {
  }

  @Override
  protected void doUnselected() {
  }

  @Override
  protected void doHoverOver() {

  }

  @Override
  protected void doHoverOff() {

  }


}
