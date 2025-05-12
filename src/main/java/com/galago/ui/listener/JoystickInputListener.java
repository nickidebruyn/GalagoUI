/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.galago.ui.listener;

import com.jme3.input.*;
import com.jme3.input.event.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The RawInputListener can be used by a user to detect when a joystick action
 * was performed.
 *
 * @author NideBruyn
 */
public class JoystickInputListener implements RawInputListener, JoystickConnectionListener {

  private ArrayList<JoystickListener> joystickListeners = new ArrayList<>();
  private InputManager inputManager;
  private boolean enabled = true;
  private JoystickEvent joystickEvent;
  private boolean debug = true;
  private float deadZone = 0.2f;
  private boolean specialSetup = false;

  private float analogStickTimer = 0;

  public JoystickInputListener() {
    joystickEvent = new JoystickEvent();

  }

  public boolean hasJoystick() {
    return inputManager.getJoysticks() != null && inputManager.getJoysticks().length > 0;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public void registerWithInput(InputManager inputManager) {
//        log("Register joystick input");
    this.inputManager = inputManager;
    this.inputManager.addRawInputListener(this);
    this.inputManager.addJoystickConnectionListener(this);
  }

  public void unregisterInput() {
//        log("Unregister joystick input");

    if (inputManager == null) {
      return;
    }

    inputManager.removeRawInputListener(this);
    inputManager.removeJoystickConnectionListener(this);

  }

  /**
   * Log some text to the console
   *
   * @param text
   */
  protected void log(String text) {
    if (debug) {
      System.out.println(text);
    }
  }

  public void addJoystickListener(JoystickListener joystickListener1) {
//        log("Joystick Input Listener: adding listener");
    this.joystickListeners.add(joystickListener1);
  }

  public void removeJoystickListener(JoystickListener joystickListener1) {
//        log("Joystick Input Listener: remove listener");
    this.joystickListeners.remove(joystickListener1);
  }

  private void fireJoystickEvent(JoystickEvent event, float tpf) {

    if (joystickListeners != null) {
//            log("Fire joystick event in: " + joystickListeners.size());

      for (int i = 0; i < joystickListeners.size(); i++) {
        JoystickListener joystickListener = joystickListeners.get(i);
        if (joystickListener != null) {
          joystickListener.stick(event, tpf);
        }
      }
      event.clearAxis();
      event.clearButtons();

//            if (event.isLeft() || event.isRight() || event.isUp() || event.isDown()) {
//                if (!event.isButtonDown()) {
//                    event.clearAxis();
//                }
//            }
    }
  }

  final private Map<JoystickAxis, Float> lastValues = new HashMap<>();

  @Override
  public void onJoyAxisEvent(JoyAxisEvent evt) {
    Float last = lastValues.remove(evt.getAxis());
    float value = evt.getValue();

    // Check the axis dead zone.  InputManager normally does this
    // by default but not for raw events like we get here.
    float effectiveDeadZone = Math.max(inputManager.getAxisDeadZone(), evt.getAxis().getDeadZone());
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

  protected void doJoystickEvent(JoyAxisEvent evt, float value) {

//        log("joystickEvent: " + joystickEvent.isButton3());
//    if (evt.getAxis().getJoystick() != null && !evt.isConsumed()) {
      joystickEvent.setJoyAxisEvent(evt);
      joystickEvent.setAnalog(evt.getAxis().isAnalog());
      joystickEvent.setAnalogValue(evt.getValue());

      //Set the joystick index
      joystickEvent.setJoystickIndex(evt.getJoyIndex());

      //Analog stick
      if (joystickEvent.isAnalog()) {
        joystickEvent.setAnalogValue(evt.getValue());

        //Joy pads
//        Debug.log("\nANALOG STICK EVENT\n-----------------------------------");
//        Debug.log("Axis: " + evt.getAxis().getLogicalId());
//        Debug.log("Value: " + evt.getValue());
        if (evt.getAxis().getLogicalId().equals(JoystickAxis.Y_AXIS) && analogStickTimer == 0) {
          if (value < 0.001f) {
            joystickEvent.setDown(true);
            joystickEvent.setAxisDown(true);
            joystickEvent.setVertical(true);
            analogStickTimer = 0.2f;

          } else if (value > 0.001f) {
            joystickEvent.setUp(true);
            joystickEvent.setAxisDown(true);
            joystickEvent.setVertical(true);
            analogStickTimer = 0.2f;

          }
//          else {
//            joystickEvent.setUp(true);
//            joystickEvent.setDown(true);
//            joystickEvent.setAxisDown(false);
//            joystickEvent.setVertical(true);
//            analogStickTimer = 0.2f;
//          }


        } else if (evt.getAxis().getLogicalId().equals(JoystickAxis.X_AXIS) && analogStickTimer == 0) {
          if (value < 0.001f) {
            joystickEvent.setLeft(true);
            joystickEvent.setAxisDown(true);
            joystickEvent.setHorizontal(true);
            analogStickTimer = 0.2f;

          } else if (value > 0.001f) {
            joystickEvent.setRight(true);
            joystickEvent.setAxisDown(true);
            joystickEvent.setHorizontal(true);
            analogStickTimer = 0.2f;

          }
//          else {
//            joystickEvent.setRight(true);
//            joystickEvent.setLeft(true);
//            joystickEvent.setAxisDown(false);
//            joystickEvent.setHorizontal(true);
//            analogStickTimer = 0.2f;
//          }
//
        }

        fireJoystickEvent(joystickEvent, 1);

      } else {
        //Direction pad
//        Debug.log("\nDIRECTION STICK EVENT\n-----------------------------------");
//        Debug.log("Axis: " + evt.getAxis().getLogicalId());
//        Debug.log("Value: " + evt.getValue());
        if (evt.getAxis().getLogicalId().equals(JoystickAxis.POV_Y) && analogStickTimer == 0) {
          if (value < 0) {
            joystickEvent.setDown(true);
            joystickEvent.setAxisDown(true);
            joystickEvent.setVertical(true);
            analogStickTimer = 0.2f;

          } else if (value > 0) {
            joystickEvent.setUp(true);
            joystickEvent.setAxisDown(true);
            joystickEvent.setVertical(true);
            analogStickTimer = 0.2f;

          }
//          else {
//            joystickEvent.setUp(true);
//            joystickEvent.setDown(true);
//            joystickEvent.setAxisDown(false);
//            joystickEvent.setVertical(true);
//          }
//
//          fireJoystickEvent(joystickEvent, 1);

        } else if (evt.getAxis().getLogicalId().equals(JoystickAxis.POV_X) && analogStickTimer == 0) {
          if (value < 0) {
            joystickEvent.setLeft(true);
            joystickEvent.setAxisDown(true);
            joystickEvent.setHorizontal(true);
            analogStickTimer = 0.2f;

          } else if (value > 0) {
            joystickEvent.setRight(true);
            joystickEvent.setAxisDown(true);
            joystickEvent.setHorizontal(true);
            analogStickTimer = 0.2f;

          }
//          else {
//            joystickEvent.setRight(true);
//            joystickEvent.setLeft(true);
//            joystickEvent.setAxisDown(false);
//            joystickEvent.setHorizontal(true);
//            analogStickTimer = 0.2f;
//
//          }
//
//          fireJoystickEvent(joystickEvent, 1);

        }

        fireJoystickEvent(joystickEvent, 1);

      }

//    }

  }

  public void onJoyButtonEvent(JoyButtonEvent evt) {
    if (evt.getButton().getJoystick() != null && !evt.isConsumed()) {
//            log("\n======================= JOYSTICK BUTTON ======================");
//            System.out.println("> onJoyButtonEvent = " + evt.getButton().getButtonId() + ";   down = " + evt.isPressed());

      joystickEvent.setJoyButtonEvent(evt);
      joystickEvent.setJoystickIndex(evt.getJoyIndex());
      joystickEvent.setButtonDown(evt.isPressed());

      joystickEvent.setButton0(evt.getButton().getLogicalId().endsWith(JoystickButton.BUTTON_0));
      joystickEvent.setButton1(evt.getButton().getLogicalId().endsWith(JoystickButton.BUTTON_1));
      joystickEvent.setButton2(evt.getButton().getLogicalId().endsWith(JoystickButton.BUTTON_2));
      joystickEvent.setButton3(evt.getButton().getLogicalId().endsWith(JoystickButton.BUTTON_3));
      joystickEvent.setButton4(evt.getButton().getLogicalId().endsWith(JoystickButton.BUTTON_4));
      joystickEvent.setButton5(evt.getButton().getLogicalId().endsWith(JoystickButton.BUTTON_5));
      joystickEvent.setButton6(evt.getButton().getLogicalId().endsWith(JoystickButton.BUTTON_6));
      joystickEvent.setButton7(evt.getButton().getLogicalId().endsWith(JoystickButton.BUTTON_7));
      joystickEvent.setButton8(evt.getButton().getLogicalId().endsWith(JoystickButton.BUTTON_8));
      joystickEvent.setButton9(evt.getButton().getLogicalId().endsWith(JoystickButton.BUTTON_9));
      joystickEvent.setButton10(evt.getButton().getLogicalId().endsWith(JoystickButton.BUTTON_10));

      fireJoystickEvent(joystickEvent, 1);

//      evt.setConsumed();
    }

  }

  public void beginInput() {
  }

  public void endInput() {
  }

  public void onMouseMotionEvent(MouseMotionEvent evt) {
  }

  public void onMouseButtonEvent(MouseButtonEvent evt) {
  }

  public void onKeyEvent(KeyInputEvent evt) {
  }

  public void onTouchEvent(TouchEvent evt) {
    if (!evt.isConsumed()) {
//            log("onTouchEvent Joystick: " + evt.getKeyCode());

//            joystickEvent.clearAll();
      if (evt.getType().equals(TouchEvent.Type.KEY_DOWN)) {

        if (evt.getKeyCode() == 19 && !joystickEvent.isUp()) {
          joystickEvent.setAnalogValue(1);
          joystickEvent.setUp(true);
          joystickEvent.setAxisDown(true);
          fireJoystickEvent(joystickEvent, 1);
          return;
        }
        if (evt.getKeyCode() == 20 && !joystickEvent.isDown()) {
          joystickEvent.setAnalogValue(1);
          joystickEvent.setDown(true);
          joystickEvent.setAxisDown(true);
          fireJoystickEvent(joystickEvent, 1);
          return;
        }
        if (evt.getKeyCode() == 21 && !joystickEvent.isLeft()) {
          joystickEvent.setAnalogValue(1);
          joystickEvent.setLeft(true);
          joystickEvent.setAxisDown(true);
          fireJoystickEvent(joystickEvent, 1);
          return;
        }
        if (evt.getKeyCode() == 22 && !joystickEvent.isRight()) {
          joystickEvent.setAnalogValue(1);
          joystickEvent.setRight(true);
          joystickEvent.setAxisDown(true);
          fireJoystickEvent(joystickEvent, 1);
          return;
        }

        //Now for buttons
        if (evt.getKeyCode() == 96 && !joystickEvent.isButton3()) {
          joystickEvent.setAnalogValue(0);
          joystickEvent.setButton3(true);
          joystickEvent.setButtonDown(true);
          fireJoystickEvent(joystickEvent, 1);
          return;
        }
        if (evt.getKeyCode() == 97 && !joystickEvent.isButton4()) {
          joystickEvent.setAnalogValue(0);
          joystickEvent.setButton4(true);
          joystickEvent.setButtonDown(true);
          fireJoystickEvent(joystickEvent, 1);
          return;
        }
        if (evt.getKeyCode() == 99 && !joystickEvent.isButton1()) {
          joystickEvent.setAnalogValue(0);
          joystickEvent.setButton1(true);
          joystickEvent.setButtonDown(true);
          fireJoystickEvent(joystickEvent, 1);
          return;
        }
        if (evt.getKeyCode() == 100 && !joystickEvent.isButton2()) {
          joystickEvent.setAnalogValue(0);
          joystickEvent.setButton2(true);
          joystickEvent.setButtonDown(true);
          fireJoystickEvent(joystickEvent, 1);
          return;
        }
        if (evt.getKeyCode() == 102 && !joystickEvent.isButton5()) {
          joystickEvent.setAnalogValue(0);
          joystickEvent.setButton5(true);
          joystickEvent.setButtonDown(true);
          fireJoystickEvent(joystickEvent, 1);
          return;
        }
        if (evt.getKeyCode() == 103 && !joystickEvent.isButton6()) {
          joystickEvent.setAnalogValue(0);
          joystickEvent.setButton6(true);
          joystickEvent.setButtonDown(true);
          fireJoystickEvent(joystickEvent, 1);
          return;
        }
        if (evt.getKeyCode() == 108 && !joystickEvent.isButton9()) {
          joystickEvent.setAnalogValue(0);
          joystickEvent.setButton9(true);
          joystickEvent.setButtonDown(true);
          fireJoystickEvent(joystickEvent, 1);
          return;
        }
        if (evt.getKeyCode() == 109 && !joystickEvent.isButton10()) {
          joystickEvent.setAnalogValue(0);
          joystickEvent.setButton10(true);
          joystickEvent.setButtonDown(true);
          fireJoystickEvent(joystickEvent, 1);
          return;
        }

      }

      if (evt.getType().equals(TouchEvent.Type.KEY_UP)) {

        if ((evt.getKeyCode() == 19 || evt.getKeyCode() == 20 || evt.getKeyCode() == 21 || evt.getKeyCode() == 22)
                && joystickEvent.isAxisDown()) {
          joystickEvent.setAnalogValue(0);
          joystickEvent.setAxisDown(false);
          fireJoystickEvent(joystickEvent, 1);
          joystickEvent.clearAxis();
          return;

        } else if (joystickEvent.isButtonDown()) {
          joystickEvent.setButtonDown(false);
          joystickEvent.setAnalogValue(0);
          fireJoystickEvent(joystickEvent, 1);
          joystickEvent.clearButtons();
          return;
        }

      }
    }
  }

  @Override
  public void onConnected(Joystick joystick) {
    log("Joystick connected: " + joystick.getName());

  }

  @Override
  public void onDisconnected(Joystick joystick) {
    log("Joystick disconnected: " + joystick.getName());

  }

  public void update(float tpf) {
//    analogStickTimer -= tpf;
    analogStickTimer = 0;
    if (analogStickTimer < 0) {
      analogStickTimer = 0;

    }
  }
}
