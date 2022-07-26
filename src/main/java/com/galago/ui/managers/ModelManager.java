package com.galago.ui.managers;

import com.galago.ui.app.GalagoApplication;
import com.jme3.material.Material;
import com.jme3.scene.Spatial;

import java.util.HashMap;
import java.util.Map;

/**
 * Is used for loading and caching models and materials.
 *
 * @author nidebruyn
 */
public class ModelManager {

  private GalagoApplication application;
  private Map<String, Spatial> models = new HashMap<String, Spatial>();
  private Map<String, Material> materials = new HashMap<String, Material>();

  public ModelManager(GalagoApplication simpleApplication) {
    this.application = simpleApplication;
  }

  public void destroy() {
    models.clear();
    materials.clear();
  }

  /**
   * Must be called to cash models that wants to be loaded in the system.
   *
   * @param modelPath
   */
  public void loadModel(String modelPath) {
    Spatial spatial = application.getAssetManager().loadModel(modelPath);
    models.put(modelPath, spatial);

  }

  /**
   * Called when a model needs to be retrieved.
   * This will create a clone of the model.
   *
   * @param modelPath
   * @return
   */
  public Spatial getModel(String modelPath) {
    return models.get(modelPath).clone();
  }


  /**
   * Must be called to cash materials that wants to be loaded in the system.
   *
   * @param materialPath
   */
  public void loadMaterial(String materialPath) {
    Material material = application.getAssetManager().loadMaterial(materialPath);
    materials.put(materialPath, material);

  }

  /**
   * Called when a material needs to be retrieved.
   * This will create a clone of the material.
   *
   * @param materialPath
   * @return
   */
  public Material getMaterial(String materialPath) {
    return materials.get(materialPath).clone();
  }

}
