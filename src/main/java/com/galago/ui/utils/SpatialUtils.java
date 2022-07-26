/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.galago.ui.utils;

import com.jme3.asset.AssetManager;
import com.jme3.material.MatParam;
import com.jme3.material.MatParamTexture;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.*;
import com.jme3.scene.shape.*;
import com.jme3.texture.Texture;

/**
 * This is a spatial utility class which can be used to create or convert
 * certain spatial parameters.
 *
 * @author nidebruyn
 */
public class SpatialUtils {

  public static void updateSpatialEdge(Spatial spatial, final ColorRGBA edgeColor, final float edgeSize) {
    if (spatial != null) {
      SceneGraphVisitor sgv = new SceneGraphVisitor() {
        @Override
        public void visit(Spatial sp) {
          if (sp instanceof Geometry) {
            Geometry geom = (Geometry) sp;

            MatParam param = geom.getMaterial().getParam("EdgesColor");

            if (param != null) {
              geom.getMaterial().setColor("EdgesColor", edgeColor);
              geom.getMaterial().setFloat("EdgeSize", edgeSize);
              geom.getMaterial().setBoolean("Fog_Edges", false);

            }
          }
        }
      };

      spatial.depthFirstTraversal(sgv);
    }

  }

  public static void updateSpatialTransparency(Spatial spatial, final boolean transparent, final float opacity) {
    if (spatial != null) {
      SceneGraphVisitor sgv = new SceneGraphVisitor() {
        @Override
        public void visit(Spatial sp) {
          if (sp instanceof Geometry) {
            Geometry geom = (Geometry) sp;

            if (transparent) {
              geom.setQueueBucket(RenderQueue.Bucket.Transparent);
              geom.getMaterial().getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
              MatParam diffuseParam = geom.getMaterial().getParam("Diffuse");

              if (diffuseParam == null) {
                diffuseParam = geom.getMaterial().getParam("Color");
              }

              if (diffuseParam != null) {
                ColorRGBA col = (ColorRGBA) diffuseParam.getValue();
                diffuseParam.setValue(new ColorRGBA(col.r, col.g, col.b, opacity));
              }

            }
          }
        }
      };

      spatial.depthFirstTraversal(sgv);
    }

  }

  public static float getSpatialTransparency(Spatial spatial) {
    float alpha = 1;
    if (spatial != null) {

      if (spatial instanceof Node) {
        Node node = (Node) spatial;
        return SpatialUtils.getSpatialTransparency(node.getChild(0));

      } else if (spatial instanceof Geometry) {
        Geometry geom = (Geometry) spatial;
        MatParam diffuseParam = geom.getMaterial().getParam("Diffuse");

        if (diffuseParam == null) {
          diffuseParam = geom.getMaterial().getParam("Color");
        }

        if (diffuseParam != null) {
          ColorRGBA col = (ColorRGBA) diffuseParam.getValue();
          alpha = col.a;
        }
      }
    }

    return alpha;

  }

  public static void updateSpatialColor(Spatial spatial, ColorRGBA color) {
    if (spatial != null) {
      SceneGraphVisitor sgv = new SceneGraphVisitor() {
        @Override
        public void visit(Spatial sp) {
          if (sp instanceof Geometry) {
            Geometry geom = (Geometry) sp;
            MatParam diffuseParam = geom.getMaterial().getParam("Diffuse");

            if (diffuseParam == null) {
              diffuseParam = geom.getMaterial().getParam("Color");
            }

            if (diffuseParam != null) {
              diffuseParam.setValue(color);
            }

          }
        }
      };

      spatial.depthFirstTraversal(sgv);
    }

  }

  public static void enableWireframe(Node node, final boolean enabled) {

    SceneGraphVisitor sgv = new SceneGraphVisitor() {
      public void visit(Spatial spatial) {

        if (spatial instanceof Geometry) {

          Geometry geom = (Geometry) spatial;
          Material mat = geom.getMaterial();

          if (mat != null) {
            mat.getAdditionalRenderState().setWireframe(enabled);

          }

        }

      }
    };

    node.depthFirstTraversal(sgv);

  }

  /**
   * Helper method which converts all materials to pixelated
   *
   * @param node
   */
  public static void makePixelated(Node node) {

    SceneGraphVisitor sgv = new SceneGraphVisitor() {
      public void visit(Spatial spatial) {

        if (spatial instanceof Geometry) {

          Geometry geom = (Geometry) spatial;
          if (geom.getMaterial().getTextureParam("ColorMap") != null) {
//                        System.out.println("Found colormap");
            MatParamTexture mpt = geom.getMaterial().getTextureParam("ColorMap");
            mpt.getTextureValue().setMinFilter(Texture.MinFilter.NearestNoMipMaps);

          }

        }

      }
    };

    node.depthFirstTraversal(sgv);

  }

  /**
   * Adds a camera node to the scene
   *
   * @param parent
   * @param camera
   * @param distance
   * @param height
   * @param angle
   * @return
   */
  public static Node addCameraNode(Node parent, Camera camera, float distance, float height, float angle) {
    final Node targetNode = new Node("camera-link");

    CameraNode cameraNode = new CameraNode("camera-node", camera);
    cameraNode.setLocalTranslation(0, height, -distance);
    cameraNode.rotate(angle * FastMath.DEG_TO_RAD, 0, 0);
    targetNode.attachChild(cameraNode);

    parent.attachChild(targetNode);

    return targetNode;
  }

  /**
   * Add a simple box to the node.
   *
   * @param parent
   * @param xExtend
   * @param yExtend
   * @param zExtend
   * @return
   */
  public static Spatial addBox(Node parent, float xExtend, float yExtend, float zExtend) {

    Box box = new Box(xExtend, yExtend, zExtend);
    Geometry geometry = new Geometry("box", box);
    parent.attachChild(geometry);
//        geometry.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
//        TangentUtils.generateBindPoseTangentsIfNecessary(box);

    return geometry;
  }

  /**
   * Add a line to the scene
   *
   * @param parent
   * @param start
   * @param end
   * @param linewidth
   * @return
   */
  public static Spatial addLine(AssetManager assetManager, Node parent, Vector3f start, Vector3f end, ColorRGBA color, float linewidth) {

    Line line = new Line(start, end);
    line.setLineWidth(linewidth);
    Geometry geometry = new Geometry("line", line);
    parent.attachChild(geometry);
    geometry.setShadowMode(RenderQueue.ShadowMode.Off);

    Material m = addColor(assetManager, geometry, color, true);
    m.getAdditionalRenderState().setLineWidth(linewidth);

    return geometry;
  }

  /**
   * Add a sphere to the scene.
   *
   * @param parent
   * @param zSamples
   * @param radialSamples
   * @param radius
   * @return
   */
  public static Spatial addSphere(Node parent, int zSamples, int radialSamples, float radius) {

    Sphere sphere = new Sphere(zSamples, radialSamples, radius);
    Geometry geometry = new Geometry("sphere", sphere);
    parent.attachChild(geometry);
    geometry.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);

    return geometry;
  }

  public static Spatial addCone(Node parent, int radialSamples, float radius, float height) {

    Cylinder c = new Cylinder(2, radialSamples, 0.0001f, radius, height, true, false);
    Geometry geometry = new Geometry("cone", c);
    parent.attachChild(geometry);
    geometry.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);

    return geometry;
  }

  /**
   * Add a cyclinder to the scene.
   *
   * @param parent
   * @param axisSamples
   * @param radialSamples
   * @param radius
   * @param height
   * @param closed
   * @return
   */
  public static Spatial addCylinder(Node parent, int axisSamples, int radialSamples, float radius, float height, boolean closed) {

    Cylinder cylinder = new Cylinder(axisSamples, radialSamples, radius, height, closed);
    Geometry geometry = new Geometry("cylinder", cylinder);
    parent.attachChild(geometry);
    geometry.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);

    return geometry;
  }

  /**
   * Add a simple plane to the node.
   *
   * @param parent
   * @param xExtend
   * @param zExtend
   * @return
   */
  public static Spatial addPlane(Node parent, float xExtend, float zExtend) {

    Quad quad = new Quad(xExtend * 2, zExtend * 2);
    Geometry geometry = new Geometry("quad", quad);
    geometry.rotate(-FastMath.DEG_TO_RAD * 90, 0, 0);
    geometry.move(-xExtend, 0, zExtend);
    parent.attachChild(geometry);
    geometry.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);

    return geometry;
  }

  /**
   * Add a simple plane to the node.
   *
   * @param parent
   * @param xExtend
   * @param zExtend
   * @return
   */
  public static Spatial addQuad(Node parent, float xExtend, float zExtend) {

    Quad quad = new Quad(xExtend * 2, zExtend * 2);
    Geometry geometry = new Geometry("quad", quad);
//        geometry.rotate(-FastMath.DEG_TO_RAD * 90, 0, 0);
//        geometry.move(-xExtend, 0, zExtend);
    parent.attachChild(geometry);
    geometry.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);

    return geometry;
  }

  /**
   * Add color to the spatial.
   *
   * @param colorRGBA
   * @return
   */
  public static Material addColor(AssetManager assetManager, Spatial spatial, ColorRGBA colorRGBA, boolean unshaded) {
    Material material = null;

    if (unshaded) {
      material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
      material.setColor("Color", colorRGBA);

    } else {
      material = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
      material.setBoolean("UseMaterialColors", true);
      material.setColor("Ambient", colorRGBA);
      material.setColor("Diffuse", colorRGBA);

    }

    spatial.setMaterial(material);

    return material;
  }
}
