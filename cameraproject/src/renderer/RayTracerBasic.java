package renderer;

import java.util.List;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

/**
 * A basic class that will implement RayTracerBase
 */
public class RayTracerBasic extends RayTracerBase {

	/**
	 * Constructor who accepts the scene and calls the father constructor
	 * 
	 * @param scene of the virtual world we have built
	 */
	public RayTracerBasic(Scene scene) {
		super(scene);
	}

	/**
	 * Function for finding the color of a point in the scene
	 * 
	 * @param point3d - A point in the scene, which is on a geometric shape
	 * @return Color at the given point
	 */
	private Color calcColor(Point3D point3d) {
		return scene.ambientLight.getIntensity();
	}

	@Override
	protected Color traceRay(Ray ray) {
		List<Point3D> allPoint3ds = scene.geometries.findIntersections(ray);
		if (allPoint3ds == null)
			return scene.background;
		Point3D point3d = ray.findClosestPoint(allPoint3ds);
		return calcColor(point3d);
	}

}