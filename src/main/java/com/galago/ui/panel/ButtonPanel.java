/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.galago.ui.panel;

import com.galago.ui.Widget;
import com.galago.ui.button.TouchButton;
import com.galago.ui.utils.Debug;
import com.jme3.input.*;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.event.*;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * A button panel will group TouchButtons together and allow the user to rotate
 * over all the buttons highlighting one at a time.
 *
 * @author Nidebruyn
 */
public class ButtonPanel extends Panel implements ActionListener, RawInputListener {

  private TouchButton selectedButton;
  private boolean verticalSelection = true;

  private String uid = UUID.randomUUID().toString();

  private Joystick joystick;

  private float deadZone = 0.01f;

  private boolean stickDown_Down = false;
  private boolean stickDown_Up = false;

  private float analogStickTimer = 0;

  public ButtonPanel(Widget parent) {
    super(parent);
    loadExtra();
  }

  public ButtonPanel(Widget parent, float width, float height) {
    super(parent, null, width, height);
    loadExtra();
  }

  public ButtonPanel(Widget parent, String pictureFile, float width, float height) {
    super(parent, pictureFile, width, height);
    loadExtra();
  }

  public ButtonPanel(Widget parent, String pictureFile, float width, float height, boolean lockScale) {
    super(parent, pictureFile, width, height, lockScale);
    loadExtra();
  }

  protected void loadExtra() {
    widgetNode.addControl(new AbstractControl() {
      @Override
      protected void controlUpdate(float tpf) {
        if (isVisible()) {
          analogStickTimer -= tpf;
          if (analogStickTimer < 0) {
            analogStickTimer = 0;
          }

        }
      }

      @Override
      protected void controlRender(RenderManager rm, ViewPort vp) {

      }
    });
  }

  private void registerInput() {
    window.getInputManager().addMapping("keyboard_up" + uid, new KeyTrigger(KeyInput.KEY_UP));
    window.getInputManager().addMapping("keyboard_down" + uid, new KeyTrigger(KeyInput.KEY_DOWN));
    window.getInputManager().addMapping("keyboard_enter_pressed" + uid, new KeyTrigger(KeyInput.KEY_RETURN));

    window.getInputManager().addMapping("keyboard_left" + uid, new KeyTrigger(KeyInput.KEY_LEFT));
    window.getInputManager().addMapping("keyboard_right" + uid, new KeyTrigger(KeyInput.KEY_RIGHT));
    window.getInputManager().addMapping("keyboard_space_pressed" + uid, new KeyTrigger(KeyInput.KEY_SPACE));

    window.getInputManager().addListener(this, "keyboard_up" + uid, "keyboard_down" + uid, "keyboard_enter_pressed" + uid, "keyboard_left" + uid, "keyboard_right" + uid, "keyboard_space_pressed" + uid);

    //window.getApplication().getJoystickInputListener().addJoystickListener(this);
    window.getInputManager().addRawInputListener(this);
  }

  private void unregisterInput() {
    window.getInputManager().deleteMapping("keyboard_up" + uid);
    window.getInputManager().deleteMapping("keyboard_down" + uid);
    window.getInputManager().deleteMapping("keyboard_enter_pressed" + uid);
    window.getInputManager().deleteMapping("keyboard_left" + uid);
    window.getInputManager().deleteMapping("keyboard_right" + uid);
    window.getInputManager().deleteMapping("keyboard_space_pressed" + uid);

    window.getInputManager().removeListener(this);
//    window.getApplication().getJoystickInputListener().removeJoystickListener(this);
    window.getInputManager().removeRawInputListener(this);
  }

  @Override
  public void onAction(String name, boolean isPressed, float tpf) {
//        if (isPressed) {
//            Debug.log("Key: " + name);
//        }

    if (isPressed &&
            ((verticalSelection && name.equals("keyboard_up" + uid)) ||
                    (!verticalSelection && name.equals("keyboard_left" + uid)))) {
      swapDown(tpf);

    } else if (isPressed &&
            ((verticalSelection && name.equals("keyboard_down" + uid)) ||
                    (!verticalSelection && name.equals("keyboard_right" + uid)))) {
      swapUp(tpf);

    } else if (name.equals("keyboard_enter_pressed" + uid) || name.equals("keyboard_space_pressed" + uid)) {

      if (selectedButton != null) {
        if (isPressed) {
          selectedButton.fireTouchDown(0, 0, tpf);
        } else {
          selectedButton.fireTouchUp(0, 0, tpf);
        }
      }
    }

  }

  private void swapUp(float tpf) {
//        if (selectedButton != null) {
    int index = widgets.indexOf(selectedButton);
//            Debug.log("index: " + index + "; size: " + widgets.size());
    index++;
    if (index > widgets.size() - 1) {
      index = 0;
    }
    selectedButton = (TouchButton) widgets.get(index);
    updateSelection(tpf, selectedButton);
//        }
  }

  private void swapDown(float tpf) {
//        if (selectedButton != null) {
    int index = widgets.indexOf(selectedButton);
    Debug.log("index: " + index + "; size: " + widgets.size());
    index--;

    if (index < 0) {
      index = widgets.size() - 1;
    }
    selectedButton = (TouchButton) widgets.get(index);
    updateSelection(tpf, selectedButton);
//        }
  }

  private void updateSelection(float tpf, TouchButton touchButton) {
    if (this.getWidgets() != null) {
      for (int i = 0; i < this.getWidgets().size(); i++) {
        Widget widget = this.getWidgets().get(i);
        if (widget instanceof TouchButton) {
          ((TouchButton) widget).unselect(tpf);
        }
      }
    }

    if (touchButton != null) {
      touchButton.select(tpf);
    }
  }

  @Override
  public void setVisible(boolean visible) {
    super.setVisible(visible); //To change body of generated methods, choose Tools | Templates.

    if (visible) {
      if (!window.getInputManager().hasMapping("keyboard_up" + uid)) {
        registerInput();
      }


    } else {

      if (window.getInputManager().hasMapping("keyboard_up" + uid)) {
        unregisterInput();
      }

      selectedButton = null;
    }

  }

//  @Override
//  public void stick(JoystickEvent joystickEvent, float fps) {
//
//    deadZone = 0.5f;
//
//    if (verticalSelection && joystickEvent.isUp() && joystickEvent.isAxisDown()) {
//      if (!stickDown_Down && (joystickEvent.getJoyAxisEvent().getValue() > deadZone)) {
//        stickDown_Down = true;
//        Debug.log("\nSWAP DOWN VERTICAL\n------------------------------------");
//        Debug.log("DeadZone: " + deadZone);
//        Debug.log("Axis down: " + joystickEvent.isAxisDown());
//        Debug.log("JoyAxisValue: " + joystickEvent.getJoyAxisEvent().getValue());
//        Debug.log("Vertical: " + joystickEvent.isVertical());
//        Debug.log("Horizontal: " + joystickEvent.isHorizontal());
//        swapDown(fps);
//      } else if (joystickEvent.getJoyAxisEvent().getValue() < deadZone && joystickEvent.getJoyAxisEvent().getValue() > 0) {
//        stickDown_Down = false;
//      }
//
//    } else if (!verticalSelection && joystickEvent.isLeft() && joystickEvent.isAxisDown()) {
//      if (!stickDown_Down && (joystickEvent.getJoyAxisEvent().getValue() > deadZone)) {
//        stickDown_Down = true;
//        Debug.log("\nSWAP DOWN HORIZONTAL\n------------------------------------");
//        Debug.log("DeadZone: " + deadZone);
//        Debug.log("Axis down: " + joystickEvent.isAxisDown());
//        Debug.log("JoyAxisValue: " + joystickEvent.getJoyAxisEvent().getValue());
//        Debug.log("Vertical: " + joystickEvent.isVertical());
//        Debug.log("Horizontal: " + joystickEvent.isHorizontal());
//        swapDown(fps);
//      } else if (joystickEvent.getJoyAxisEvent().getValue() < deadZone && joystickEvent.getJoyAxisEvent().getValue() > 0) {
//        stickDown_Down = false;
//      }
//
//    }
//
//    if (verticalSelection && joystickEvent.isDown() && joystickEvent.isAxisDown()) {
//      if (!stickDown_Up && (joystickEvent.getJoyAxisEvent().getValue() < -deadZone)) {
//        Debug.log("\nSWAP UP VERTICAL\n------------------------------------");
//        Debug.log("DeadZone: " + deadZone);
//        Debug.log("Axis down: " + joystickEvent.isAxisDown());
//        Debug.log("JoyAxisValue: " + joystickEvent.getJoyAxisEvent().getValue());
//        Debug.log("Vertical: " + joystickEvent.isVertical());
//        Debug.log("Horizontal: " + joystickEvent.isHorizontal());
//        swapUp(fps);
//
//      } else if (joystickEvent.getJoyAxisEvent().getValue() > -deadZone && joystickEvent.getJoyAxisEvent().getValue() < 0) {
//        stickDown_Up = false;
//
//      }
//
//    } else if (!verticalSelection && joystickEvent.isRight() && joystickEvent.isAxisDown()) {
//      if (!stickDown_Up && (joystickEvent.getJoyAxisEvent().getValue() < -deadZone)) {
//        Debug.log("\nSWAP UP HORIZONTAL\n------------------------------------");
//        Debug.log("DeadZone: " + deadZone);
//        Debug.log("Axis down: " + joystickEvent.isAxisDown());
//        Debug.log("JoyAxisValue: " + joystickEvent.getJoyAxisEvent().getValue());
//        Debug.log("Vertical: " + joystickEvent.isVertical());
//        Debug.log("Horizontal: " + joystickEvent.isHorizontal());
//        swapUp(fps);
//
//      } else if (joystickEvent.getJoyAxisEvent().getValue() > -deadZone && joystickEvent.getJoyAxisEvent().getValue() < 0) {
//        stickDown_Up = false;
//
//      }
//
//    }
////
////    if (joystickEvent.isAxisDown()) {
////      if (joystickEvent.isUp() || joystickEvent.isLeft()) {
////        swapDown(fps);
////      }
////      if (joystickEvent.isDown() || joystickEvent.isRight()) {
////        swapUp(fps);
////      }
////    }
//
//
//    if ((joystickEvent.isButton1() || joystickEvent.isButton3()) && selectedButton != null) {
//      if (joystickEvent.isButtonDown()) {
//        selectedButton.fireTouchDown(0, 0, 1f);
//      } else {
//        selectedButton.fireTouchUp(0, 0, 1f);
//      }
//    }
//
//  }

  private void doJoystickEvent(JoyAxisEvent evt, float value) {

    if (evt.getAxis().isAnalog()) {
      //Joy pads
//      Debug.log("\nANALOG STICK EVENT\n-----------------------------------");
//      Debug.log("Axis: " + evt.getAxis().getLogicalId());
//      Debug.log("Value: " + value);
      if (evt.getAxis().getLogicalId().equals(JoystickAxis.Y_AXIS) && verticalSelection && analogStickTimer == 0) {
        if (value < 0) {
          swapDown(value);
          analogStickTimer = 0.2f;
        } else if (value > 0) {
          swapUp(value);
          analogStickTimer = 0.2f;
        }

      } else if (evt.getAxis().getLogicalId().equals(JoystickAxis.X_AXIS) && !verticalSelection && analogStickTimer == 0) {
        if (value < 0) {
          swapDown(value);
          analogStickTimer = 0.2f;
        } else if (value > 0) {
          swapUp(value);
          analogStickTimer = 0.2f;
        }

      }


    } else {
      //Direction pad
//      Debug.log("\nDIRECTION STICK EVENT\n-----------------------------------");
//      Debug.log("Axis: " + evt.getAxis().getLogicalId());
//      Debug.log("Value: " + value);
      if (evt.getAxis().getLogicalId().equals(JoystickAxis.POV_Y) && verticalSelection) {
        if (value > 0) {
          swapDown(value);
        } else if (value < 0) {
          swapUp(value);
        }

      } else if (evt.getAxis().getLogicalId().equals(JoystickAxis.POV_X) && !verticalSelection) {
        if (value > 0) {
          swapDown(value);
        } else if (value < 0) {
          swapUp(value);
        }

      }

    }

  }

  final private Map<JoystickAxis, Float> lastValues = new HashMap<>();

  @Override
  public void onJoyAxisEvent(JoyAxisEvent evt) {
    Float last = lastValues.remove(evt.getAxis());
    float value = evt.getValue();

    // Check the axis dead zone.  InputManager normally does this
    // by default but not for raw events like we get here.
    float effectiveDeadZone = 0.5f;//Math.max(window.getInputManager().getAxisDeadZone(), evt.getAxis().getDeadZone());
    if (Math.abs(value) < effectiveDeadZone) {
      if (last == null) {
        // Just skip the event
        return;
      }
      // Else set the value to 0
      lastValues.remove(evt.getAxis());
      value = 0;
    }
    doJoystickEvent(evt, value);

    if (value != 0) {
      lastValues.put(evt.getAxis(), value);
    }
  }

  @Override
  public void onJoyButtonEvent(JoyButtonEvent evt) {
    Debug.log("\nButton Event:\n------------------------------------");
    Debug.log("Button id: " + evt.getButton().getLogicalId());
    if (selectedButton != null) {
      if (evt.getButton().getLogicalId().endsWith(JoystickButton.BUTTON_0) ||
              evt.getButton().getLogicalId().endsWith(JoystickButton.BUTTON_2)) {
        if (evt.isPressed()) {
          selectedButton.fireTouchDown(0, 0, 1f);
        } else {
          selectedButton.fireTouchUp(0, 0, 1f);
        }
      }
    }

  }

  @Override
  public void beginInput() {
  }

  @Override
  public void endInput() {
  }

  @Override
  public void onMouseMotionEvent(MouseMotionEvent evt) {
  }

  @Override
  public void onMouseButtonEvent(MouseButtonEvent evt) {
  }

  @Override
  public void onKeyEvent(KeyInputEvent evt) {
  }

  @Override
  public void onTouchEvent(TouchEvent evt) {
    Debug.log("\n=============================\nTouch Event: " + evt);
  }

  public boolean isVerticalSelection() {
    return verticalSelection;
  }

  public void setVerticalSelection(boolean verticalSelection) {
    this.verticalSelection = verticalSelection;
  }
}
