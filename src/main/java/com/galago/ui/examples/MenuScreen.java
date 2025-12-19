package com.galago.ui.examples;

import com.galago.ui.Label;
import com.galago.ui.button.TouchButton;
import com.galago.ui.effect.HoverColorEffect;
import com.galago.ui.listener.TouchButtonAdapter;
import com.galago.ui.screens.AbstractScreen;
import com.jme3.math.ColorRGBA;

public class MenuScreen extends AbstractScreen {

  public static final String NAME = "MENU";

  private Label title;
  private TouchButton playButton;
  private TouchButton settingsButton;
  private TouchButton helpButton;
  private TouchButton exitButton;

  @Override
  protected void init() {
    title = new Label(hudPanel, "Main Menu", 48, 600, 100);
    title.centerTop(0, 50);

    playButton = new TouchButton(hudPanel, "play-button", "Play Game");
    playButton.updatePicture("Interface/blank.png");
    playButton.setFontSize(28);
    playButton.centerAt(0, 100);
    playButton.addEffect(new HoverColorEffect(playButton, ColorRGBA.White, ColorRGBA.Orange));
    playButton.addTouchButtonListener(new TouchButtonAdapter() {
      @Override
      public void doTouchUp(float touchX, float touchY, float tpf, String uid) {
        showScreen(GameScreen.NAME);
      }
    });

    settingsButton = new TouchButton(hudPanel, "settingsButton", "Settings");
    settingsButton.updatePicture("Interface/blank.png");
    settingsButton.setFontSize(28);
    settingsButton.centerAt(0, 20);
    settingsButton.addEffect(new HoverColorEffect(settingsButton, ColorRGBA.White, ColorRGBA.Orange));

    helpButton = new TouchButton(hudPanel, "helpButton", "Help");
    helpButton.updatePicture("Interface/blank.png");
    helpButton.setFontSize(28);
    helpButton.centerAt(0, -60);
    helpButton.addEffect(new HoverColorEffect(helpButton, ColorRGBA.White, ColorRGBA.Orange));

    exitButton = new TouchButton(hudPanel, "exitButton", "Exit");
    exitButton.updatePicture("Interface/blank.png");
    exitButton.setFontSize(28);
    exitButton.centerAt(0, -140);
    exitButton.addEffect(new HoverColorEffect(exitButton, ColorRGBA.White, ColorRGBA.Orange));
    exitButton.addTouchButtonListener(new TouchButtonAdapter() {
      @Override
      public void doTouchUp(float touchX, float touchY, float tpf, String uid) {
        exitScreen();
      }
    });

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
