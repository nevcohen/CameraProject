package renderer;

import elements.LightSource;
import geometries.Geometry;
import geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Ray;
import static primitives.Util.*;

import java.util.List;

import primitives.Vector;
import scene.Scene;

/**
 * A basic class that will implement RayTracerBase
 */
public class RayTracerBasic extends RayTracerBase {

	/**
	 * ------------------
	 */
	private static final double DELTA = 0.1;

	/**
	 * Constructor who accepts the scene and calls the father constructor
	 * 
	 * @param scene of the virtual world we have built
	 */
	public RayTracerBasic(Scene scene) {
		super(scene);
	}

	/**
	 * Function to calculate the color of a point in the scene.
	 * 
	 * @param intersection - A point in the scene, which is on a geometric shape
	 * @param ray          - The ray from the camera that hit the above point
	 * @return Color at the given point
	 */
	private Color calcColor(GeoPoint intersection, Ray ray) {
		return scene.ambientLight.getIntensity().add(intersection.geometry.getEmission())
				.add(calcLocalEffects(intersection, ray));
	}

	/**
	 * Add calculated light contribution from all light sources.
	 * 
	 * @param intersection - A point in the scene, which is on a geometric shape
	 * @param ray          - The ray from the camera that hit the above point
	 * @return The color of the geometry according to the local variables, such as
	 *         the material and the light sources.
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
				if (unshaded(lightSource, l, n, p)) {
					r = l.subtract(n.scale(2 * ln));
					Color lightIntensity = lightSource.getIntensity(p);
					color = color.add(calcDiffusive(material.kD, ln, lightIntensity),
							calcSpecular(material.kS, r, v, material.nShininess, lightIntensity));
				}
			}
		}
		return color;
	}

	/**
	 * Calculating the Color of the specular component of the object for the Phong
	 * model.
	 * 
	 * @param kS             - The specular coefficient
	 * @param r              - The reflection of the light
	 * @param v              - The direction of the camera
	 * @param nShininess     - The shininess of the object
	 * @param lightIntensity - The intensity of the light
	 * @return The total specular component of the current object.
	 */
	private Color calcSpecular(double kS, Vector r, Vector v, int nShininess, Color lightIntensity) {
		double vr = alignZero(-v.dotProduct(r));
		if (vr <= 0)
			return Color.BLACK;
		vr = Math.pow(vr, nShininess);
		return lightIntensity.scale(kS * vr);
	}

	/**
	 * Calculating the Color of the diffuse component of the object for the Phong
	 * model.
	 * 
	 * @param kD             - The diffuse coefficient
	 * @param ln             - The dot product of the lights' vector with the normal
	 *                       of the body
	 * @param lightIntensity - The intensity of the light
	 * @return The final diffuse component of the given object for the phong model.
	 */
	private Color calcDiffusive(double kD, double ln, Color lightIntensity) {
		if (ln < 0)
			ln = -ln;
		return lightIntensity.scale(kD * ln);
	}

	/**
	 * Check if a particular point is shaded or not
	 * 
	 * @param ls - The light directed to the point (p)
	 * @param l  - The vector from light to point (p)
	 * @param n  - The normal at the point (p)
	 * @param p  - A point where the light is in its direction
	 * @return False - there is shadow, True - there is no shadow
	 */
	private boolean unshaded(LightSource ls, Vector l, Vector n, Point3D p) {
		Vector lightDirection = l.scale(-1); // from point to light source
		Vector delta = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);
		Ray lightRay = new Ray(p.add(delta), lightDirection);
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, ls.getDistance(p));
		return intersections == null;
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