package com.galago.ui.examples;

import com.galago.ui.Label;
import com.galago.ui.button.TouchButton;
import com.galago.ui.effect.HoverColorEffect;
import com.galago.ui.listener.TouchButtonAdapter;
import com.galago.ui.panel.GridPanel;
import com.galago.ui.panel.HPanel;
import com.galago.ui.screens.AbstractScreen;
import com.jme3.math.ColorRGBA;

public class MenuScreen extends AbstractScreen {

  public static final String NAME = "MENU";

  private Label title;
  private TouchButton button1;
  private TouchButton screenSize1;
  private TouchButton screenSize2;
  private TouchButton screenSize3;
  private HPanel settingsPanel;
  private GridPanel screensPanel;

  protected void createScreenButton(GridPanel gridPanel, String screenName, String text) {
    button1 = new TouchButton(gridPanel, screenName, text);
    button1.addEffect(new HoverColorEffect(button1, ColorRGBA.White, ColorRGBA.Orange));
    button1.addTouchButtonListener(new TouchButtonAdapter() {
      @Override
      public void doTouchUp(float touchX, float touchY, float tpf, String uid) {
        log("Clicked on : " + uid);
        showScreen(uid);

      }
    });
  }

  @Override
  protected void init() {
    log("Init " + NAME);

    title = new Label(hudPanel, "Main Menu", 48, 600, 100);
    title.centerTop(0, 50);

    //##### SCREENS
    screensPanel = new GridPanel(hudPanel, null, 700, 400);
    screensPanel.center();
    hudPanel.add(screensPanel);
    {
      createScreenButton(screensPanel, ButtonsScreen.NAME, "Buttons");
    }
    screensPanel.layout(5, 4);


    //#######################################################
    settingsPanel = new HPanel(hudPanel, null, 800, 50);
    hudPanel.add(settingsPanel);
    settingsPanel.rightBottom(0, 0);
    {
      screenSize1 = new TouchButton(settingsPanel, "screenSize1-button", "720 x 480");
      screenSize1.leftBottom(5, 5);
      screenSize1.addEffect(new HoverColorEffect(screenSize1, ColorRGBA.White, ColorRGBA.Orange));
      screenSize1.addTouchButtonListener(new TouchButtonAdapter() {
        @Override
        public void doTouchUp(float touchX, float touchY, float tpf, String uid) {
          application.resizeScreen(false, 720, 480);
        }

      });

      screenSize2 = new TouchButton(settingsPanel, "screenSize2-button", "1280 x 720");
      screenSize2.centerBottom(0, 5);
      screenSize2.addEffect(new HoverColorEffect(screenSize2, ColorRGBA.White, ColorRGBA.Orange));
      screenSize2.addTouchButtonListener(new TouchButtonAdapter() {
        @Override
        public void doTouchUp(float touchX, float touchY, float tpf, String uid) {
          application.resizeScreen(false, 1280, 720);
        }
      });

      screenSize3 = new TouchButton(settingsPanel, "screenSize3-button", "1920 x 1080");
      screenSize3.rightBottom(5, 5);
      screenSize3.addEffect(new HoverColorEffect(screenSize3, ColorRGBA.White, ColorRGBA.Orange));
      screenSize3.addTouchButtonListener(new TouchButtonAdapter() {
        @Override
        public void doTouchUp(float touchX, float touchY, float tpf, String uid) {
          application.resizeScreen(false, 1920, 1080);
        }
      });
    }

    settingsPanel.layout();
  }

  @Override
  protected void load() {

  }

  @Override
  protected void show() {

  }

  @Override
  protected void exit() {

  }

  @Override
  protected void pause() {

  }
}
