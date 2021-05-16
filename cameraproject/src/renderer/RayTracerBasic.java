package renderer;

import elements.LightSource;
import geometries.Geometry;
import geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Ray;
import static primitives.Util.*;
import primitives.Vector;
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
	 * @param intersection - A point in the scene, which is on a geometric shape
	 * @param ray          - The ray from the camera that hit the above point
	 * @return Color at the given point
	 */
	private Color calcColor(GeoPoint intersection, Ray ray) {
		return scene.ambientLight.getIntensity().add(intersection.geometry.getEmission()).add(calcLocalEffects(intersection, ray));
	}

	/**
	 * Add calculated light contribution from all light sources
	 * ------------------
	 * @param intersection
	 * @param ray
	 * @return
	 */
	private Color calcLocalEffects(GeoPoint intersection, Ray ray) {

		Geometry geometry = intersection.geometry;
		Point3D p = intersection.point;
		Material material = geometry.getMaterial();

		Vector l, r, n = geometry.getNormal(p), v = ray.getDir();
		double ln, sign = alignZero(v.dotProduct(n));

		Color color = Color.BLACK;
		if (sign == 0)
			return color;

		for (LightSource lightSource : scene.lights) {
			l = lightSource.getL(p);
			ln = alignZero(l.dotProduct(n));
			if (sign * ln > 0) { // sign(ln) == sing(vn)
				r = l.subtract(n.scale(2 * ln));
				Color lightIntensity = lightSource.getIntensity(p);
				color = color.add(calcDiffusive(material.kD, ln, lightIntensity),
						calcSpecular(material.kS, r, v, material.nShininess, lightIntensity));
			}
		}
		return color;
	}

	/**
	 * -----------------
	 * 
	 * @param kS
	 * @param r
	 * @param v
	 * @param nShininess
	 * @param lightIntensity
	 * @return
	 */
	private Color calcSpecular(double kS, Vector r, Vector v, int nShininess, Color lightIntensity) {
		double vr = alignZero(0 - v.dotProduct(r));
		if (vr <= 0)
			return Color.BLACK;
		vr = Math.pow(vr, nShininess);
		return lightIntensity.scale(kS * vr);
	}

	/**
	 * --------------------
	 * 
	 * @param kD
	 * @param ln
	 * @param lightIntensity
	 * @return
	 */
	private Color calcDiffusive(double kD, double ln, Color lightIntensity) {
		return lightIntensity.scale(kD * Math.abs(ln));
	}

	@Override
	public Color traceRay(Ray ray) {
		var intersections = scene.geometries.findGeoIntersections(ray);
		if (intersections == null)
			return scene.background;
		GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
		return calcColor(closestPoint, ray);
	}

}