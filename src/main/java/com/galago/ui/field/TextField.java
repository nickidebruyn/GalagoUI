/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.galago.ui.field;

import com.galago.ui.FontStyle;
import com.galago.ui.ImageWidget;
import com.galago.ui.TextAlign;
import com.galago.ui.app.GalagoApplication;
import com.galago.ui.button.TouchKeyNames;
import com.galago.ui.effect.Effect;
import com.galago.ui.listener.FocusListener;
import com.galago.ui.listener.KeyboardListener;
import com.galago.ui.listener.TouchButtonListener;
import com.galago.ui.managers.FontManager;
import com.galago.ui.panel.Panel;
import com.galago.ui.ttf.TrueTypeFont;
import com.galago.ui.ttf.shapes.TrueTypeContainer;
import com.galago.ui.ttf.util.StringContainer;
import com.galago.ui.utils.Debug;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.font.LineWrapMode;
import com.jme3.font.Rectangle;
import com.jme3.input.InputManager;
import com.jme3.input.KeyNames;
import com.jme3.input.RawInputListener;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.event.*;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;

import java.util.Properties;

/**
 * A TextField to enter text with the keyboard.
 *
 * @author nidebruyn
 */
public class TextField extends ImageWidget implements InputType {

  protected Panel panel;
  protected InputManager inputManager;
  protected Camera cam;
  protected CollisionResults results;
  protected Ray ray;
  protected int uid;
  protected String id;
  protected TouchButtonListener touchButtonListener;
  protected ActionListener actionListener;
  private boolean enabled = true;
  private boolean wasDown = false;
  protected BitmapFont bitmapFont;
  protected BitmapText bitmapText;
  protected TrueTypeFont trueTypeFont;
  protected TrueTypeContainer trueTypeContainer;
  protected StringContainer stringContainer;
  protected FontStyle fontStyle;
  private boolean focus = false;
  protected int maxLength = 10;
  protected float padding = 0;
  protected boolean caps = false;
  protected boolean shift = false;
  protected KeyboardListener keyboardListener;
  protected FocusListener focusListener;
  protected KeyNames keyNames = new KeyNames();
  protected TouchKeyNames touchKeyNames = new TouchKeyNames();
  private String textToUpdate;

  /**
   * @param panel
   * @param id
   */
  public TextField(Panel panel, String id) {
    this(panel, id, "Interface/textfield.png", 300, 40);
  }

  public TextField(Panel panel, String id, float width) {
    this(panel, id, "Interface/textfield.png", width, 40);
  }

  public TextField(Panel panel, String id, float width, float height) {
    this(panel, id, "Interface/textfield.png", width, height);
  }

  /**
   * @param panel
   * @param id
   * @param pictureFile
   */
  public TextField(Panel panel, String id, String pictureFile) {
    this(panel, id, pictureFile, 300, 40);
  }

  public TextField(Panel panel, String id, String pictureFile, float width, float height) {
    this(panel, id, pictureFile, width, height, false);
  }

  public TextField(Panel panel, String id, String pictureFile, float width, float height, boolean lockScale) {
    this(panel, id, pictureFile, width, height, 5, new FontStyle(FontManager.DEFAULT_FONT, 18), false);
  }

  /**
   * @param panel
   * @param id
   * @param pictureFile
   * @param width
   * @param height
   */
  public TextField(Panel panel, String id, String pictureFile, float width, float height, float padding, FontStyle fontStyle, boolean lockScale) {
    super(panel.getWindow(), panel, pictureFile, width, height, lockScale);
    this.id = id;
    this.fontStyle = fontStyle;
    this.padding = window.getScaleFactorWidth() * padding;

    bitmapFont = panel.getWindow().getApplication().getFontManager().getBitmapFonts(fontStyle);

    if (bitmapFont != null) {
      //Init the text
      bitmapText = bitmapFont.createLabel(id);
      bitmapText.setText("Text");             // the text
      Rectangle rectangle = new Rectangle((-getWidth() * 0.5f) + padding, (getHeight() * 0.45f) - padding, getWidth() - padding, (getHeight() * 0.5f) - padding);
//            System.out.println("TextField Rectange = " + rectangle);
      bitmapText.setBox(rectangle);
      bitmapText.setSize(fontStyle.getFontSize() * panel.getWindow().getScaleFactorHeight());      // font size
      bitmapText.setLineWrapMode(LineWrapMode.NoWrap);
      bitmapText.setColor(ColorRGBA.DarkGray);// font color
      bitmapText.setAlignment(BitmapFont.Align.Left);
      bitmapText.setVerticalAlignment(BitmapFont.VAlign.Center);
      widgetNode.attachChild(bitmapText);
    } else {
      //Init the text
      trueTypeFont = panel.getWindow().getApplication().getFontManager().getTtfFonts(fontStyle);
      stringContainer = new StringContainer(trueTypeFont, "Text", 0, new Rectangle((-getWidth() * 0.5f) + padding, (getHeight() * 0.5f) - padding, getWidth() - padding, (getHeight()) - padding));
      stringContainer.setWrapMode(StringContainer.WrapMode.NoWrap);
      stringContainer.setAlignment(StringContainer.Align.Left);
      stringContainer.setVerticalAlignment(StringContainer.VAlign.Center);
      trueTypeContainer = trueTypeFont.getFormattedText(stringContainer, ColorRGBA.DarkGray, ColorRGBA.DarkGray);

      widgetNode.attachChild(trueTypeContainer);
    }

    widgetNode.addControl(new AbstractControl() {
      @Override
      protected void controlUpdate(float tpf) {
        if (textToUpdate != null) {
          setText(textToUpdate);
          textToUpdate = null;
        }
      }

      @Override
      protected void controlRender(RenderManager rm, ViewPort vp) {

      }
    });

//        //Init the text
//        float xP = -getWidth() * 0.5f;
//        float yP = getHeight() * 0.5f;
//        float recWidth = getWidth();
//        float factor = 1f;
//        float recHeight = (getHeight()*0.5f) * factor;
//        float padding = 0f;
//               
//        bitmapText = window.getBitmapFont().createLabel(id);
//        bitmapText.setText(" ");
//        bitmapText.setBox(new Rectangle(xP+padding, yP, recWidth+padding, recHeight));
//        bitmapText.setSize(fontSize * window.getScaleFactorHeight());      // font size
//        bitmapText.setColor(ColorRGBA.DarkGray);// font color
//        bitmapText.setAlignment(BitmapFont.Align.Left);
//        bitmapText.setVerticalAlignment(BitmapFont.VAlign.Center);
//        bitmapText.setLineWrapMode(LineWrapMode.NoWrap);
//        widgetNode.attachChild(bitmapText);
    this.inputManager = window.getInputManager();
    this.cam = window.getApplication().getCamera();
    this.results = new CollisionResults();
    this.ray = new Ray(cam.getLocation(), cam.getDirection());

    actionListener = new ActionListener() {
      public void onAction(String name, boolean isPressed, float tpf) {
        results.clear();

        if (isVisible() && isEnabled()) {

          if ((TextField.this.id + "MOUSE").equals(name)) {

            // 1. calc direction
            Vector3f origin = new Vector3f(inputManager.getCursorPosition().x, inputManager.getCursorPosition().y, 1f);
            Vector3f direction = new Vector3f(0, 0, -1);

            // 2. Aim the ray from cam loc to cam direction.
            ray.setOrigin(origin);
            ray.setDirection(direction);

            // 3. Collect intersections between Ray and Shootables in results list.
            window.getWindowNode().collideWith(ray, results);

            // 5. Use the results (we mark the hit object)
            if (results.size() > 0) {

              for (int i = 0; i < results.size(); i++) {
                CollisionResult cr = results.getCollision(i);
//                                System.out.println("\t-> Hit: " + cr.getGeometry().getParent().getName());

                if (widgetNode.hasChild(cr.getGeometry())) {
//                                    System.out.println("\t\t\tCollision -> " + TouchButton.this.id);
                  if (isPressed) {
                    wasDown = true;
                    getWindow().removeFocusFromFields();
                    focus = true;
                    fireFocusListener(TextField.this.id);
                  }
                }
              }
            }

          }
        }

      }
    };

    inputManager.addRawInputListener(new RawInputListener() {
      public void beginInput() {
      }

      public void endInput() {
      }

      public void onJoyAxisEvent(JoyAxisEvent evt) {
      }

      public void onJoyButtonEvent(JoyButtonEvent evt) {
      }

      public void onMouseMotionEvent(MouseMotionEvent evt) {
      }

      public void onMouseButtonEvent(MouseButtonEvent evt) {
      }

      public void onKeyEvent(KeyInputEvent evt) {
//                System.out.println("Keyinput ***************** Key = " + evt.getKeyCode());

        if (enabled && focus && evt.isReleased()) {
          String keyChar = keyNames.getName(evt.getKeyCode());
//                    System.out.println("Keyinput ***************** code = " + evt.getKeyCode());
//                    System.out.println("Keyinput ***************** char = " + evt.getKeyChar());

          if (evt.getKeyCode() == 14) {
            if (getText().length() > 0) {
              setText(getText().substring(0, getText().length() - 1));
            }
          } else if (evt.getKeyCode() == 15) {
            focus = false;

          } else if (keyChar != null && evt.getKeyCode() == 57) {
            setText(getText() + " ");

          } else if (keyChar != null && evt.getKeyCode() == 58) {
            caps = !caps;

          } else if (keyChar != null && (evt.getKeyCode() == 42 || evt.getKeyCode() == 54)) {
            caps = false;

          } else if (keyChar != null && keyChar.length() == 1) {
            if (!caps) {
              keyChar = keyChar.toLowerCase();
            }
            setText(getText() + keyChar);
          }

          if (getText().length() > maxLength) {
            setText(getText().substring(0, maxLength));
          }

          fireKeyboardListener(evt);

        } else if (enabled && focus && !evt.isReleased()) {
          String keyChar = keyNames.getName(evt.getKeyCode());
//                    System.out.println("Keyinput ***************** code = " + evt.getKeyCode());
//                    System.out.println("Keyinput ***************** char = " + evt.getKeyChar());

          if (keyChar != null && (evt.getKeyCode() == 42 || evt.getKeyCode() == 54)) {
            caps = true;

          }

          fireKeyboardListener(evt);

        }

      }

      public void onTouchEvent(TouchEvent evt) {
//                System.out.println("Touchinput ***************** Keycode = " + evt.getKeyCode());

//                if (enabled && focus && evt.getType().equals(TouchEvent.Type.KEY_DOWN)) {
//                    String keyChar = touchKeyNames.getName(evt.getKeyCode());
//                    System.out.println("\n\n\nTouchinput ***************** KeyCode = " + evt.getKeyCode());
//
//                    if (evt.getKeyCode() == 67) { //backspace
//                        if (getText().length() > 0) {
//                            setText(getText().substring(0, getText().length() - 1));
//                        }
//
//                    } else if (keyChar != null && evt.getKeyCode() == 62) { //space
//                        setText(getText() + " ");
//
//                    } else if (keyChar != null && evt.getKeyCode() == 59) { //shift
//                        caps = !caps;
//
//                    } else if (keyChar != null && keyChar.length() == 1) {
//                        //TODO:
////                        if (!caps) {
//                        keyChar = keyChar.toLowerCase();
////                        }
//                        setText(getText() + keyChar);
//                    }
//
//                    if (getText().length() > maxLength) {
//                        setText(getText().substring(0, maxLength));
//                    }
//                    
//                    
//
////                    if (evt.getKeyCode() == 67) {
////                        if (getText().length() > 0) {
////                            setText(getText().substring(0, getText().length()-1));
////                        }                        
////                        
////                    } else if (evt.getKeyCode() == 59) {
////                        if (getText().length() > 0) {
////                            setText(getText().substring(0, getText().length()-1));
////                        }                        
////                        
////                    } else if (keyChar != null && keyChar.length() == 1) {
////                        setText(getText() + keyChar);
////                    }
////                    
////                    if (getText().length() > maxLength) {
////                        setText(getText().substring(0, maxLength));
////                    }
//
//                }
      }
    });

    //NICKI
    panel.add(this);

//        bitmapText.setLocalTranslation(bitmapText.getLocalTranslation().x, bitmapText.getLocalTranslation().y, 0.001f);
    if (window.getApplication().isMobileApp()) {
      addFocusListener(new FocusListener() {
        public void doFocus(String id) {
          Properties p = new Properties();
          p.setProperty(GalagoApplication.NAME, getText());
          window.getApplication().fireKeyboardInputListener(p, TextField.this);
        }
      });
    }

  }

  @Override
  protected boolean isBatched() {
    return false;
  }

  public void addKeyboardListener(KeyboardListener keyboardListener) {
    this.keyboardListener = keyboardListener;
  }

  protected void fireKeyboardListener(KeyInputEvent event) {
    if (keyboardListener != null) {
      keyboardListener.doKeyPressed(event);
    }
  }

  public void addFocusListener(FocusListener focusListener1) {
    this.focusListener = focusListener1;
  }

  protected void fireFocusListener(String id) {
    if (focusListener != null) {
      focusListener.doFocus(id);
    }
  }

  /**
   * @param maxLength
   */
  public void setMaxLength(int maxLength) {
    this.maxLength = maxLength;
  }

  /**
   * @param align
   */
  public void setTextAlignment(TextAlign align) {
    if (bitmapText != null) {
      switch (align) {
        case LEFT:
          bitmapText.setAlignment(BitmapFont.Align.Left);
          break;
        case RIGHT:
          bitmapText.setAlignment(BitmapFont.Align.Right);
          break;
        case CENTER:
          bitmapText.setAlignment(BitmapFont.Align.Center);
          break;
      }

    } else if (stringContainer != null) {
      switch (align) {
        case LEFT:
          stringContainer.setAlignment(StringContainer.Align.Left);
          break;
        case RIGHT:
          stringContainer.setAlignment(StringContainer.Align.Right);
          break;
        case CENTER:
          stringContainer.setAlignment(StringContainer.Align.Center);
          break;
      }
      this.trueTypeContainer.updateGeometry();
    }

  }

  /**
   * @param align
   */
  public void setTextVerticalAlignment(TextAlign align) {
    if (bitmapText != null) {
      switch (align) {
        case TOP:
          bitmapText.setVerticalAlignment(BitmapFont.VAlign.Top);
          break;
        case BOTTOM:
          bitmapText.setVerticalAlignment(BitmapFont.VAlign.Bottom);
          break;
        case CENTER:
          bitmapText.setVerticalAlignment(BitmapFont.VAlign.Center);
          break;
      }

    } else if (stringContainer != null) {
      switch (align) {
        case TOP:
          stringContainer.setVerticalAlignment(StringContainer.VAlign.Top);
          break;
        case BOTTOM:
          stringContainer.setVerticalAlignment(StringContainer.VAlign.Bottom);
          break;
        case CENTER:
          stringContainer.setVerticalAlignment(StringContainer.VAlign.Center);
          break;
      }
      this.trueTypeContainer.updateGeometry();
    }
  }

  private boolean isTextEmpty(String text) {
    return text == null || text.length() == 0 || text.equals(" ");
  }

  /**
   * @param text
   */
  //This fixes the out of memory opengl error we get in android.
  public void setText(String text) {

    if (isTextEmpty(text)) {
      if (bitmapText != null) {
        this.bitmapText.removeFromParent();

      } else if (stringContainer != null) {
        this.trueTypeContainer.removeFromParent();

      }
    } else if (bitmapText != null) {
      this.bitmapText.setText(text);
      if (this.bitmapText.getParent() == null) {
        widgetNode.attachChild(this.bitmapText);
      }

    } else if (stringContainer != null) {
      this.stringContainer.setText(text);
      if (this.trueTypeContainer.getParent() == null) {
        widgetNode.attachChild(this.trueTypeContainer);
      }
      this.trueTypeContainer.updateGeometry();

    }

  }

  public String getText() {
    if (bitmapText != null) {
      if (this.bitmapText.getParent() == null) {
        return "";
      } else {
        return this.bitmapText.getText();
      }

    } else if (stringContainer != null) {
      if (this.trueTypeContainer.getParent() == null) {
        return "";
      } else {
        return this.stringContainer.getText();
      }

    } else {
      return null;
    }
  }

  /**
   * @param colorRGBA
   */
  public void setTextColor(ColorRGBA colorRGBA) {
    if (bitmapText != null) {
      this.bitmapText.setColor(colorRGBA);

    } else if (stringContainer != null) {
      this.trueTypeContainer.getMaterial().setColor("Color", colorRGBA);
      this.trueTypeContainer.updateGeometry();

    }
  }

  /**
   * This will set the outline color if there is an outline
   *
   * @param colorRGBA
   */
  public void setOutlineColor(ColorRGBA colorRGBA) {
    if (stringContainer != null) {
      this.trueTypeContainer.getMaterial().setColor("Outline", colorRGBA);
      this.trueTypeContainer.updateGeometry();

    }
  }

  /**
   * @param size
   */
  public void setFontSize(float size) {

    if (bitmapText != null) {
      bitmapText.setSize(size * window.getScaleFactorHeight());// font size

    } else if (stringContainer != null) {
      //TODO
      Debug.log("WARNING: FontSize not supported on TouchButton: " + id);
    }

  }

  @Override
  public void add(Node parent) {
    super.add(parent);
    String mappingName = TextField.this.id + "MOUSE";
    if (!inputManager.hasMapping(mappingName)) {
      inputManager.addMapping(mappingName, new MouseButtonTrigger(0));
    }

    inputManager.addListener(actionListener, TextField.this.id + "MOUSE");
  }

  @Override
  public void remove() {
    super.remove();
    inputManager.removeListener(actionListener);
  }

  //    @Override
//    public void setVisible(boolean visible) {
//        super.setVisible(visible);
//        
//        if (visible) {
//            inputManager.addListener(actionListener, TextField.this.id + "MOUSE");
//            bitmapText.setCullHint(Spatial.CullHint.Never);
//        } else {
//            inputManager.removeListener(actionListener);
//            bitmapText.setCullHint(Spatial.CullHint.Always);
//        }
//    }
  @Override
  public void setVisible(boolean visible) {
    super.setVisible(visible);

    if (bitmapText != null) {
      if (visible && widgetNode.getCullHint().equals(Spatial.CullHint.Always)) {
        inputManager.addListener(actionListener, TextField.this.id + "MOUSE");
        bitmapText.setCullHint(Spatial.CullHint.Never);

      } else if (!visible && widgetNode.getCullHint().equals(Spatial.CullHint.Never)) {
        inputManager.removeListener(actionListener);
        bitmapText.setCullHint(Spatial.CullHint.Always);
      }

    } else if (stringContainer != null) {
      if (visible && widgetNode.getCullHint().equals(Spatial.CullHint.Always)) {
        inputManager.addListener(actionListener, TextField.this.id + "MOUSE");
        trueTypeContainer.setCullHint(Spatial.CullHint.Never);
      } else if (!visible && widgetNode.getCullHint().equals(Spatial.CullHint.Never)) {
        inputManager.removeListener(actionListener);
        trueTypeContainer.setCullHint(Spatial.CullHint.Always);
      }

    }
  }

  public boolean isEnabled() {
    return enabled;
  }

  /**
   * @param enabled
   */
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
    for (Effect effect : effects) {
      effect.fireEnabled(enabled);
    }
  }

  public void blur() {
    focus = false;
  }

  @Override
  public void updateText(String text) {
    this.textToUpdate = text;
  }

}
