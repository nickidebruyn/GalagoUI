/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.galago.ui.field;

import com.galago.ui.ImageWidget;
import com.galago.ui.TextAlign;
import com.galago.ui.panel.Panel;
import com.jme3.math.ColorRGBA;
import com.jme3.ui.Picture;

/**
 * The progressBar class is exactly that. 2 Images layered over each other to
 * represent a Horizontal ProgressBar.
 *
 * @author nidebruyn
 */
public class ProgressBar extends ImageWidget {

  protected Panel panel;
  protected float progress = 1;
  protected Picture progressPicture;
  protected ColorRGBA progressPictureColor = new ColorRGBA(1, 1, 1, 1);
  protected String progressPictureFile;
  protected float padding = 0;
  protected float borderWidth = 5;
  protected float progressDepth = -0.1f;
  protected TextAlign progressAlignment = TextAlign.LEFT;

  /**
   * @param panel
   * @param pictureFile
   * @param progressPictureFile
   */
  public ProgressBar(Panel panel, String pictureFile, String progressPictureFile) {
    this(panel, pictureFile, progressPictureFile, 250, 40);
  }

  /**
   * @param panel
   * @param pictureFile
   * @param progressPictureFile
   * @param width
   * @param height
   */
  public ProgressBar(Panel panel, String pictureFile, String progressPictureFile, float width, float height) {
    super(panel.getWindow(), panel, pictureFile, width, height, false);
    this.progressPictureFile = progressPictureFile;

    if (progressPictureFile != null) {
//            Texture2D texture2D = (Texture2D) window.getAssetManager().loadTexture(progressPictureFile);
      progressPicture = new Picture("PROGRESS-IMAGE-WIDGET");
//            progressPicture.setImage(window.getAssetManager(), progressPictureFile, true);
//            progressPicture.setMaterial(window.makeGuiMaterial(texture2D));
      progressPicture.setMaterial(window.getApplication().getTextureManager().getGUIMaterial(progressPictureFile));

      progressPicture.setWidth(getWidth());
      progressPicture.setHeight(getHeight());
      progressPicture.move(-getWidth() * 0.5f, -getHeight() * 0.5f, 0f);
      widgetNode.attachChild(progressPicture);

//            window.addPictureForOptimization(picture);
    }

    setProgress(progress);
  }

  /**
   * @param progress
   */
  public void setProgress(float progress) {
    this.progress = progress;
    float pad = borderWidth - (borderWidth * progress);
    progressPicture.setWidth(getWidth() * progress);

    if (progressAlignment.equals(TextAlign.RIGHT)) {
      progressPicture.setLocalTranslation((-getWidth() * 0.5f) + ((padding * window.getScaleFactorWidth()) * (1f - progress)) + pad, -getHeight() * 0.5f, progressDepth);

    } else {
      progressPicture.setLocalTranslation((-getWidth() * 0.5f) + ((padding * window.getScaleFactorWidth()) * (1f - progress)) + pad, -getHeight() * 0.5f, progressDepth);
    }

  }

  public float getBorderWidth() {
    return borderWidth;
  }

  public void setBorderWidth(float borderWidth) {
    this.borderWidth = borderWidth;
  }

  public float getProgress() {
    return progress;
  }

  public void setProgressAlignment(TextAlign progressAlignment) {
    this.progressAlignment = progressAlignment;
  }

  @Override
  protected boolean isBatched() {
    return false;
  }

  public float getPadding() {
    return padding;
  }

  public void setPadding(float padding) {
    this.padding = padding;
  }

  public float getProgressDepth() {
    return progressDepth;
  }

  public void setProgressDepth(float progressDepth) {
    this.progressDepth = progressDepth;
  }

  public void setProgressTransparency(float alpha) {
    if (progressPicture != null && progressPicture.getMaterial() != null) {
      progressPictureColor.set(1f, 1f, 1f, alpha);
      progressPicture.getMaterial().setColor("Color", progressPictureColor);
    }
  }

}
