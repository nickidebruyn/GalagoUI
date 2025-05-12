package com.galago.ui.examples;

import com.galago.ui.app.GalagoApplication;
import com.galago.ui.managers.*;
import com.jme3.math.ColorRGBA;

import java.io.IOException;

public class Example extends GalagoApplication {

  public Example() {
    super("Example App", 1280, 720, "example1-galago.save", null, null, false);
  }

  /**
   * The main method for this java app when we run it.
   *
   * @param args
   */
  public static void main(String[] args) {
    new Example();
  }

  @Override
  protected void preInitApp() {
    BACKGROUND_COLOR = ColorRGBA.Gray;

  }

  @Override
  protected void postInitApp() {
    showScreen(MenuScreen.NAME);
    showStats();

  }

  @Override
  protected void initPhysics() {

  }

  @Override
  protected void initScreens(ScreenManager screenManager) {
    screenManager.loadScreen(MenuScreen.NAME, new MenuScreen());
    screenManager.loadScreen(ButtonsScreen.NAME, new ButtonsScreen());

  }


  @Override
  public void initModelManager(ModelManager modelManager) {

  }

  @Override
  protected void initSound(SoundManager soundManager) {

  }

  @Override
  protected void initEffect(EffectManager effectManager) {

  }

  @Override
  protected void initTextures(TextureManager textureManager) {

  }

  @Override
  protected void initFonts(FontManager fontManager) {

  }

  @Override
  protected Object[] getIconList() throws IOException {
    return new Object[0];
  }
}
