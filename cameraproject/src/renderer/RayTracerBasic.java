package renderer;

import geometries.Intersectable.GeoPoint;
import primitives.Color;
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
	 * Function to calculate the color of a point in the scene
	 * 
	 * @param point3d - A point in the scene, which is on a geometric shape
	 * @return Color at the given point
	 */
	private Color calcColor(GeoPoint geoPoint) {
		return scene.ambientLight.getIntensity().add(geoPoint.geometry.getEmission());
	}

	@Override
	public Color traceRay(Ray ray) {
		var intersections = scene.geometries.findGeoIntersections(ray);
		if (intersections == null)
			return scene.background;
		GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
		return calcColor(closestPoint);
	}

}