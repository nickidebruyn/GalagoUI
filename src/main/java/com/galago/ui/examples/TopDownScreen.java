package com.galago.ui.examples;

import com.galago.ui.screens.AbstractScreen;
import com.jme3.scene.Spatial;

public class TopDownScreen extends AbstractScreen {

  public static final String NAME = "TOPDOWN";

  private Spatial player;

  @Override
  protected void init() {
    log("Init " + NAME);

  }

  @Override
  protected void load() {


  }

  @Override
  protected void show() {
    setPreviousScreen(null);

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
