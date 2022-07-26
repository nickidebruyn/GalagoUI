package com.galago.ui.examples;

import com.galago.ui.app.GalagoApplication;
import com.galago.ui.managers.*;
import com.jme3.math.ColorRGBA;

public class LowRez extends GalagoApplication {

  public LowRez() {
    super("Low Rez", 64, 64, "example2-galago.save", null, null, false);
  }

  /**
   * The main method for this java app when we run it.
   *
   * @param args
   */
  public static void main(String[] args) {
    new LowRez();
  }

  @Override
  protected void preInitApp() {
    BACKGROUND_COLOR = ColorRGBA.Gray;

  }

  @Override
  protected void postInitApp() {
    showScreen(LowRezScreen.NAME);

  }

  @Override
  protected void initPhysics() {

  }

  @Override
  protected void initScreens(ScreenManager screenManager) {
    screenManager.loadScreen(LowRezScreen.NAME, new LowRezScreen());

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
}
