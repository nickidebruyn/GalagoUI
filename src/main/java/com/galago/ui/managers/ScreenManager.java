package com.galago.ui.managers;

import com.galago.ui.app.GalagoApplication;
import com.galago.ui.screens.AbstractScreen;

import java.util.HashMap;
import java.util.Map;

/**
 * This class will manage the screens and cache them.
 *
 * @author NideBruyn
 */
public class ScreenManager {

  private GalagoApplication application;
  private Map<String, AbstractScreen> screens = new HashMap<String, AbstractScreen>();

  public ScreenManager(GalagoApplication simpleApplication) {
    this.application = simpleApplication;
  }

  public void destroy() {
    screens.clear();
  }

  /**
   * Must be called to cash screens that wants to be loaded in the system.
   *
   * @param screenName
   */
  public void loadScreen(String screenName, AbstractScreen abstractScreenState) {
    application.getStateManager().attach(abstractScreenState);
    screens.put(screenName, abstractScreenState);
    abstractScreenState.setScreenName(screenName);

  }

  /**
   * Called when a screen needs to be retrieved. This will return the screen
   *
   * @param screenName
   * @return
   */
  public AbstractScreen getScreen(String screenName) {
    return screens.get(screenName);
  }

  public Map<String, AbstractScreen> getScreens() {
    return screens;
  }


}
