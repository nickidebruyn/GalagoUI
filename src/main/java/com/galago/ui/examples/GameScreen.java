package com.galago.ui.examples;

import com.galago.ui.Label;
import com.galago.ui.button.TouchButton;
import com.galago.ui.effect.HoverColorEffect;
import com.galago.ui.listener.TouchButtonAdapter;
import com.galago.ui.screens.AbstractScreen;
import com.jme3.math.ColorRGBA;

public class GameScreen extends AbstractScreen {

  public static final String NAME = "GameScreen";

  private Label title;
  private TouchButton backButton;

  @Override
  protected void init() {

    title = new Label(hudPanel, "Playing game", 48, 500, 50);
    title.centerTop(0, 50);

    backButton = new TouchButton(hudPanel, "back-button", "Back");
    backButton.centerBottom(0, 5);
    backButton.addEffect(new HoverColorEffect(backButton, ColorRGBA.White, ColorRGBA.Orange));
    backButton.addTouchButtonListener(new TouchButtonAdapter() {
      @Override
      public void doTouchUp(float touchX, float touchY, float tpf, String uid) {
        showPreviousScreen();

      }
    });

  }

  @Override
  protected void load() {

  }

  @Override
  protected void show() {
    setPreviousScreen(MenuScreen.NAME);

  }

  @Override
  protected void exit() {

  }

  @Override
  protected void pause() {

  }

  @Override
  public void update(float tpf) {

  }
}
