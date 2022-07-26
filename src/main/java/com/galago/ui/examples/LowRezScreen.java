package com.galago.ui.examples;

import com.galago.ui.screens.AbstractScreen;

public class LowRezScreen extends AbstractScreen {

  public static final String NAME = "LOWREZ";

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
