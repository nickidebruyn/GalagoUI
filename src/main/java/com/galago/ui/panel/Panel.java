/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.galago.ui.panel;

import com.galago.ui.ImageWidget;
import com.galago.ui.Widget;
import com.galago.ui.window.Window;
import com.jme3.material.Material;
import com.jme3.scene.BatchNode;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A Panel will have a set of widgets attached to it.
 *
 * @author nidebruyn
 */
public class Panel extends ImageWidget {

  protected ArrayList<Widget> widgets = new ArrayList<Widget>();

  /**
   * @param parent
   */
  public Panel(Widget parent) {
    this(parent.getWindow(), parent, "Interface/panel.png", 600, 400);
  }

  /**
   * @param window
   * @param pictureFile
   */
  public Panel(Window window, String pictureFile) {
    this(window, pictureFile, window.getWidth(), window.getHeight());
  }

  public Panel(Window window, String pictureFile, boolean lockScale) {
    this(window, pictureFile, window.getWidth(), window.getHeight(), lockScale);
  }

  /**
   * @param window
   * @param pictureFile
   * @param width
   * @param height
   */
  public Panel(Window window, String pictureFile, float width, float height) {
    super(window, null, pictureFile, width, height, false);
  }

  public Panel(Window window, String pictureFile, float width, float height, boolean lockScale) {
    super(window, null, pictureFile, width, height, lockScale);
  }

  /**
   * @param window
   * @param parent
   * @param pictureFile
   * @param width
   * @param height
   */
  public Panel(Window window, Widget parent, String pictureFile, float width, float height) {
    super(window, parent, pictureFile, width, height, false);

  }

  /**
   * @param window
   * @param parent
   * @param pictureFile
   * @param width
   * @param height
   */
  public Panel(Window window, Widget parent, String pictureFile, float width, float height, boolean lockScale) {
    super(window, parent, pictureFile, width, height, lockScale);

  }

  /**
   * @param parent
   * @param pictureFile
   * @param width
   * @param height
   */
  public Panel(Widget parent, String pictureFile, float width, float height) {
    super(parent.getWindow(), parent, pictureFile, width, height, false);

  }

  public Panel(Widget parent, String pictureFile, float width, float height, boolean lockScale) {
    super(parent.getWindow(), parent, pictureFile, width, height, lockScale);

  }

  public void add(Widget widget) {
    widgets.add(widget);
    widget.add(widgetNode);
  }

  public ArrayList<Widget> getWidgets() {
    return widgets;
  }

  /**
   * remove all widgets.
   */
  public void clear() {
    for (int i = 0; i < widgets.size(); i++) {
      Widget widget = widgets.get(i);
      widget.remove();
    }
    widgets.clear();
  }

  @Override
  public void setVisible(boolean visible) {
    super.setVisible(visible);

    for (Iterator<Widget> it = widgets.iterator(); it.hasNext(); ) {
      Widget widget = it.next();
      widget.setVisible(visible);
    }
  }

  @Override
  public void setTransparency(float alpha) {
    //the widgets
    if (widgets != null) {
      for (Iterator<Widget> it = widgets.iterator(); it.hasNext(); ) {
        Widget widget = it.next();
        widget.setTransparency(alpha);
      }
    }

    //NB: When we set the transparency of the panel we need to make sure we clone the material
    if (picture != null && picture.getMaterial() != null) {
      Material clonedMat = picture.getMaterial().clone();
      picture.setMaterial(clonedMat);

    }
    super.setTransparency(alpha);

  }

  @Override
  protected boolean isBatched() {
    return false;
  }

  /**
   * This method will try to optimize the node and all its children.
   */
  public void optimize() {
    if (widgetNode instanceof BatchNode) {
      ((BatchNode) widgetNode).batch();
    }
  }
}
