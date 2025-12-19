package com.galago.ui.examples;

import com.galago.ui.app.GalagoApplication;
import com.galago.ui.managers.*;
import com.jme3.math.ColorRGBA;

import java.io.IOException;

/**
 * In this Example1 I will show you the basic structure of a game created with GalagoUI framework.
 * It helps a lot with things you normally had to handle your self.
 * Such as a UI framework with widgets that scale automatically with the screen or window size.
 */
public class Example1 extends GalagoApplication {

  /**
   * This is the constructor which takes the title of the game, native resolution size, a save file name, a default font to use
   * if required, a splash screen if you have one and a parameter to show the default jME start dialog.
   *
   */
  public Example1() {
    super("Example1", 1280, 720, "example1.save", null, null, false);
  }

  /**
   * The main method for this java app when we run it.
   *
   * @param args
   */
  public static void main(String[] args) {
    new Example1();
  }

  @Override
  protected void preInitApp() {
    //The preInitApp() method is called before the screens and managers loads.
    BACKGROUND_COLOR = ColorRGBA.DarkGray;

  }

  @Override
  protected void postInitApp() {
    //The postInitApp() abstract method is called after the screens was initialized and all the manager abstract methods was called.
    showScreen(MenuScreen.NAME); //Here tell the app to start of by showing the MenuScreen.
    //showStats();

  }

  @Override
  protected void initPhysics() {
    //On loading this is where you can initialize a physics world if required.

  }

  @Override
  protected void initScreens(ScreenManager screenManager) {
    //If you create a screen class it should first be loaded here before you can show it.
    screenManager.loadScreen(MenuScreen.NAME, new MenuScreen());
    screenManager.loadScreen(GameScreen.NAME, new GameScreen());

  }


  @Override
  public void initModelManager(ModelManager modelManager) {
    //Here you can preload models if required to.

  }

  @Override
  protected void initSound(SoundManager soundManager) {
    //Here you can preload the sound FX or music you want to use in your game.

  }

  @Override
  protected void initEffect(EffectManager effectManager) {
    //This is where you can preload effects. Normally I load the ParticleEmitters I created here.


  }

  @Override
  protected void initTextures(TextureManager textureManager) {
    //Here you can preload textures

  }

  @Override
  protected void initFonts(FontManager fontManager) {
    //If you use TTF fonts you can predefine them or load them here.

  }

  @Override
  protected Object[] getIconList() throws IOException {
    //If you want to load app icons for your game, this is where you can do that.
//    File projectDirectory = new File(new File("").getAbsolutePath());
//    images = new Object[3];
//    images[0] = ImageIO.read(new File(projectDirectory.getPath() + "/icons/icon16.png"));
//    images[1] = ImageIO.read(new File(projectDirectory.getPath() + "/icons/icon32.png"));
//    images[2] = ImageIO.read(new File(projectDirectory.getPath() + "/icons/icon128.png"));
//    return images;

    return new Object[0];
  }
}
