package com.galago.ui;

import com.galago.ui.managers.FontManager;
import com.galago.ui.panel.Panel;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.font.LineWrapMode;
import com.jme3.font.Rectangle;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Spatial;

/**
 * A normal text label on the screen.
 * It now supports both bitmap text and true type fonts ttf
 *
 * @author nidebruyn
 */
public class Label extends Widget {

  protected Panel panel;
  protected BitmapFont bitmapFont;
  protected BitmapText bitmapText;
  protected FontStyle fontStyle;

  /**
   * @param panel
   * @param text
   */
  public Label(Panel panel, String text) {
    this(panel, text, 300, 40, new FontStyle(FontManager.DEFAULT_FONT, 18));
  }

  /**
   * @param panel
   * @param text
   * @param fontSize
   */
  public Label(Panel panel, String text, int fontSize) {
    this(panel, text, 300, 40, new FontStyle(FontManager.DEFAULT_FONT, fontSize));

  }

  public Label(Panel panel, String text, int fontSize, float width, float height) {
    this(panel, text, width, height, new FontStyle(FontManager.DEFAULT_FONT, fontSize));

  }

  /**
   * @param panel
   * @param text
   * @param width
   * @param height
   */
  public Label(Panel panel, String text, float width, float height, FontStyle fontStyle) {
    super(panel.getWindow(), panel, width, height, false);
    this.panel = panel;

    bitmapFont = panel.getWindow().getApplication().getFontManager().getBitmapFonts(fontStyle);
//        Rectangle textBox = new Rectangle(-getWidth() * 0.5f, getHeight() * 0.5f, getWidth(), getHeight() *0.5f);

    if (bitmapFont != null) {
      //Init the text
      bitmapText = bitmapFont.createLabel(text);
      bitmapText.setText(text);             // the text
      //The Rectangle box height value for bitmap text is not a physical height but half the height
      bitmapText.setBox(new Rectangle(-getWidth() * 0.5f, getHeight() * 0.5f, getWidth(), getHeight() * 0.5f));
      bitmapText.setSize(fontStyle.getFontSize() * panel.getWindow().getScaleFactorHeight());      // font size
      bitmapText.setColor(ColorRGBA.White);// font color
      bitmapText.setAlignment(BitmapFont.Align.Center);
      bitmapText.setVerticalAlignment(BitmapFont.VAlign.Center);
//            widgetNode.attachChild(bitmapText);
    }

    panel.add(this);

    setText(text);

//        bitmapText.setLocalTranslation(bitmapText.getLocalTranslation().x, bitmapText.getLocalTranslation().y, 0.001f);
  }


  public void setWrapMode(LineWrapMode lineWrapMode) {
    if (bitmapText != null) {
      this.bitmapText.setLineWrapMode(lineWrapMode);

    }
  }

  /**
   * @param align
   */
  public void setAlignment(TextAlign align) {
    if (bitmapText != null) {
      switch (align) {
        case LEFT:
          bitmapText.setAlignment(BitmapFont.Align.Left);
          break;
        case RIGHT:
          bitmapText.setAlignment(BitmapFont.Align.Right);
          break;
        case CENTER:
          bitmapText.setAlignment(BitmapFont.Align.Center);
          break;
      }

    }

  }

  /**
   * @param align
   */
  public void setVerticalAlignment(TextAlign align) {
    if (bitmapText != null) {
      switch (align) {
        case TOP:
          bitmapText.setVerticalAlignment(BitmapFont.VAlign.Top);
          break;
        case BOTTOM:
          bitmapText.setVerticalAlignment(BitmapFont.VAlign.Bottom);
          break;
        case CENTER:
          bitmapText.setVerticalAlignment(BitmapFont.VAlign.Center);
          break;
      }

    }
  }

  private boolean isTextEmpty(String text) {
    return text == null || text.length() == 0 || text.equals(" ");
  }

  /**
   * @param text
   */
  //This fixes the out of memory opengl error we get in android.
  public void setText(String text) {

    if (isTextEmpty(text)) {
      if (bitmapText != null) {
        this.bitmapText.removeFromParent();

      }
    } else {
      if (bitmapText != null) {
        this.bitmapText.setText(text);
        if (this.bitmapText.getParent() == null) widgetNode.attachChild(this.bitmapText);

      }
    }

  }

  /**
   * Return the text value of this button
   *
   * @return
   */
  public String getText() {
    if (bitmapText != null) {
      if (this.bitmapText.getParent() == null) {
        return "";
      } else {
        return this.bitmapText.getText();
      }

    } else {
      return null;
    }
  }

  /**
   * @param colorRGBA
   */
  public void setTextColor(ColorRGBA colorRGBA) {
    if (bitmapText != null) {
      this.bitmapText.setColor(colorRGBA);

    }
  }

  @Override
  public void setVisible(boolean visible) {
    super.setVisible(visible);

    if (bitmapText != null) {
      if (visible && widgetNode.getCullHint().equals(Spatial.CullHint.Always)) {
        bitmapText.setCullHint(Spatial.CullHint.Never);
      } else if (!visible && widgetNode.getCullHint().equals(Spatial.CullHint.Never)) {
        bitmapText.setCullHint(Spatial.CullHint.Always);
      }

    }
  }

  /**
   * @param size
   */
  public void setFontSize(float size) {

    if (bitmapText != null) {
      bitmapText.setSize(size * window.getScaleFactorHeight());// font size

    }
  }

  @Override
  public void setTransparency(float alpha) {
    if (bitmapText != null) {
      bitmapText.setAlpha(alpha);

    }
  }

  @Override
  public float getTransparency() {
    if (bitmapText != null) {
      return bitmapText.getAlpha();

    } else {
      return 1f;
    }

  }

  @Override
  protected boolean isBatched() {
    return false;
  }
}
