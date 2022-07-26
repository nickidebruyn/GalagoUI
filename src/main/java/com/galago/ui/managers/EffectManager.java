package com.galago.ui.managers;

import com.galago.ui.app.GalagoApplication;
import com.galago.ui.controls.SpatialLifeControl;
import com.jme3.effect.ParticleEmitter;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

import java.util.HashMap;
import java.util.Map;

/**
 * Is used for handling particle effects.
 *
 * @author nidebruyn
 */
public class EffectManager {

  private GalagoApplication application;
  private Map<String, Spatial> effects = new HashMap<String, Spatial>();
  private ColorRGBA startColor;
  private ColorRGBA endColor;
  private ColorRGBA materialColor;

  public EffectManager(GalagoApplication baseApplication) {
    this.application = baseApplication;
  }

  public void destroy() {
    effects.clear();
  }

  /**
   * This method can be called when you need to override the particle effects
   * colors. This will only be applied once.
   *
   * @param startColor
   * @param endColor
   */
  public void prepareColor(ColorRGBA startColor, ColorRGBA endColor) {
    this.startColor = startColor;
    this.endColor = endColor;
  }

  public void prepareMaterialColor(ColorRGBA matColor) {
    this.materialColor = matColor;
  }

  /**
   * Load an effect using a path to a j3o model.
   *
   * @param effect
   * @param effectPath
   */
  public void loadEffect(String effect, String effectPath) {
    Spatial spatial = application.getAssetManager().loadModel(effectPath);
    loadEffect(effect, spatial);
  }

  /**
   * Load an effect using a spatial
   *
   * @param effect
   * @param effectSpatial
   */
  public void loadEffect(String effect, Spatial effectSpatial) {
    effects.put(effect, effectSpatial);
    preloadParticles(effectSpatial);
  }

  /**
   * Should be called when you want to show a particle effect.
   *
   * @param effect
   * @param position
   */
  public void doEffect(String effect, Vector3f position) {
    doEffect(effect, position, 200);
  }

  /**
   * Should be called when you want to show a particle effect.
   *
   * @param effect
   * @param position
   */
  public void doEffect(String effect, Vector3f position, float timeInMiliSec) {
    Spatial effectSpatial = (Spatial) effects.get(effect);
    doEffect(effectSpatial, position, timeInMiliSec);
  }

  /**
   * This will preload the particels
   *
   * @param spatial
   */
  protected void preloadParticles(Spatial spatial) {
    if (spatial != null && spatial instanceof Node) {
      for (int i = 0; i < ((Node) spatial).getQuantity(); i++) {
        Spatial s = ((Node) spatial).getChild(i);
        if (s instanceof ParticleEmitter) {
          ParticleEmitter emitter = (ParticleEmitter) s;
          emitter.preload(application.getRenderManager(), application.getViewPort());
        }
      }
    }
  }

  /**
   * This will go through a spatial object and emit All particles that might
   * be located.
   *
   * @param spatial
   */
  protected void doParticleRespawn(Spatial spatial) {
    if (spatial != null && spatial instanceof Node) {
      for (int i = 0; i < ((Node) spatial).getQuantity(); i++) {
        Spatial s = ((Node) spatial).getChild(i);
        if (s instanceof ParticleEmitter) {
          ParticleEmitter emitter = (ParticleEmitter) s;
          if (startColor != null) {
            emitter.setStartColor(startColor);
          }
          if (endColor != null) {
            emitter.setStartColor(endColor);
          }

          if (materialColor != null) {
            emitter.getMaterial().setColor("Color", materialColor);

          }

          emitter.emitAllParticles();
        }
      }
      startColor = null;
      endColor = null;
      materialColor = null;
    }
  }

  /**
   * Helper method to prevent dupplication
   *
   * @param spatial
   * @param position
   */
  protected void doEffect(Spatial spatial, Vector3f position, float activeTimeInMilliSec) {
    Spatial sp = spatial.clone();
    sp.setCullHint(Spatial.CullHint.Never);
    sp.setLocalTranslation(position);
    SpatialLifeControl control = new SpatialLifeControl(activeTimeInMilliSec);
    sp.addControl(control);

    if (application.getCurrentScreen() != null) {
      application.getCurrentScreen().getRootNode().attachChild(sp);
    } else {
      application.getRootNode().attachChild(sp);
    }

    //Call the particle respawn
    doParticleRespawn(sp);
    control.start();
  }

}
