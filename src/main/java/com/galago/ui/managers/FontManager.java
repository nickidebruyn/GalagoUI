package com.galago.ui.managers;

import com.galago.ui.FontStyle;
import com.galago.ui.app.GalagoApplication;
import com.galago.ui.ttf.TrueTypeFont;
import com.galago.ui.ttf.TrueTypeKey;
import com.galago.ui.ttf.util.Style;
import com.jme3.font.BitmapFont;

import java.util.HashMap;
import java.util.Map;

/**
 * Is used for handling loading of different fonts.
 *
 * @author nidebruyn
 */
public class FontManager {

  public static String DEFAULT_FONT = "Interface/Fonts/OpenSans-Bold.ttf";
  private GalagoApplication application;
  private Map<String, TrueTypeFont> ttfFonts = new HashMap<String, TrueTypeFont>();
  private Map<String, BitmapFont> bitmapFonts = new HashMap<String, BitmapFont>();

  public FontManager(GalagoApplication galagoApplication) {
    this.application = galagoApplication;
  }

  public void destroy() {
    ttfFonts.clear();
    bitmapFonts.clear();
  }

  public boolean isBitmapFont(FontStyle fontStyle) {
    return fontStyle.getFontFile().endsWith(".fnt") || fontStyle.getFontFile().endsWith(".FNT");
  }

  public boolean isTrueTypeFont(FontStyle fontStyle) {
    return fontStyle.getFontFile().endsWith(".ttf") || fontStyle.getFontFile().endsWith(".TTF");
  }

  /**
   * Load a font type
   */
  public void loadFont(FontStyle fontStyle) {

    if (ttfFonts.get(fontStyle.getUniqueKey()) != null || bitmapFonts.get(fontStyle.getUniqueKey()) != null) {
      application.log("WARNING, font already loaded: " + fontStyle.toString());
      return;
    }

    if (isTrueTypeFont(fontStyle)) {
      Style style = null;
      if (fontStyle.isItalic()) {
        style = Style.Italic;
      } else {
        style = Style.Plain;
      }
      TrueTypeKey ttk = new TrueTypeKey(fontStyle.getFontFile(), style,
              (int) (fontStyle.getFontSize() * application.getApplicationHeightScaleFactor()),
              (int) (fontStyle.getOutlineSize() * application.getApplicationHeightScaleFactor()));
      TrueTypeFont ttf = (TrueTypeFont) application.getAssetManager().loadAsset(ttk);
      ttf.getBitmapGlyphs("ABCDEFGHIJKLMNOPQRSTUVWXYZ"
              + "abcdefghijklmnopqrstuvwxyz"
              + "0123456789!@#$%^&*()-_+=*/"
              + "\\:;\"<>,.?{}[]|`~'");
      ttf.lockAtlas(true);
      ttf.reloadTexture();

      ttfFonts.put(fontStyle.getUniqueKey(), ttf);

    } else if (isBitmapFont(fontStyle)) {
      BitmapFont bf = application.getAssetManager().loadFont(fontStyle.getFontFile());
      bitmapFonts.put(fontStyle.getFontFile(), bf);

    } else {
      throw new RuntimeException("Unsupported font format. Load only \".ttf\" or \".fnt\"");
    }

  }

  public TrueTypeFont getTtfFonts(FontStyle fontStyle) {
    TrueTypeFont ttf = ttfFonts.get(fontStyle.getUniqueKey());
    if (ttf == null && isTrueTypeFont(fontStyle)) {
      throw new RuntimeException("WARNING: True type font was not preloaded in BaseApplication " + fontStyle.getUniqueKey());
    }
    return ttf;
  }

  public BitmapFont getBitmapFonts(FontStyle fontStyle) {
    BitmapFont bf = bitmapFonts.get(fontStyle.getFontFile());
    if (bf == null && isBitmapFont(fontStyle)) {
      throw new RuntimeException("WARNING: Bitmap font was not preloaded in BaseApplication " + fontStyle.getUniqueKey());
    }
    return bf;
  }
}
