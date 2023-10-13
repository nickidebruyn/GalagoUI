package com.galago.ui.examples;

import com.galago.ui.app.GalagoApplication;
import com.galago.ui.managers.*;
import com.jme3.math.ColorRGBA;

public class ExampleTopDown extends GalagoApplication {

    public ExampleTopDown() {
        super("Top Down", 1280, 720, "example-topdown.save", null, null, false);
    }

    /**
     * The main method for this java app when we run it.
     *
     * @param args
     */
    public static void main(String[] args) {
        new ExampleTopDown();
    }

    @Override
    protected void preInitApp() {
        BACKGROUND_COLOR = ColorRGBA.DarkGray;

    }

    @Override
    protected void postInitApp() {
        showScreen(TopDownScreen.NAME);

    }

    @Override
    protected void initPhysics() {

    }

    @Override
    protected void initScreens(ScreenManager screenManager) {
        screenManager.loadScreen(TopDownScreen.NAME, new TopDownScreen());

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
