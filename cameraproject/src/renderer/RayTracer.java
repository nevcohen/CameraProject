package renderer;

import static primitives.Util.alignZero;

import java.util.List;

import elements.LightSource;
import geometries.Geometry;
import geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import renderer.VoxelsGrid.RayVoxels;
import scene.Scene;

public class RayTracer extends RayTracerBasic {

	private VoxelsGrid voxelsGrid;

	/**
	 * The maximum depth we have determined for the reflection and refraction in
	 * advance.
	 */
	private static final int MAX_CALC_COLOR_LEVEL = 10;
	/**
	 * The lowest we will lower the reflection and refractions coefficient to,
	 * before we deemed it to become irrelevant.
	 */
	private static final double MIN_CALC_COLOR_K = 0.001;
	/**
	 * The initial value of the reflection and refractions coefficient.
	 */
	private static final double INITIAL_K = 1.0;

	public RayTracer(Scene scene, double voxelSize) {
		super(scene);
		voxelsGrid = new VoxelsGrid(scene, voxelSize);
	}

	/**
	 * Function to calculate the color of a point in the scene.
	 * 
	 * @param intersection - A point in the scene, which is on a geometric shape.
	 * @param ray          - The ray from the camera that hit the above point.
	 * @return Color at the given point
	 */
	private Color calcColor(GeoPoint intersection, Ray ray) {
		return calcColor(intersection, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K).add(scene.ambientLight.getIntensity());
	}

	/**
	 * Function to calculate the color of a point in the scene, with additional
	 * checks for reflection and refraction, to determine global effects and local
	 * effects.
	 * 
	 * @param intersection - A point in the scene, which is on a geometric shape.
	 * @param ray          - The ray from the camera that hit the above point.
	 * @param level        - The current depth of the recursion.
	 * @param k            - The current coefficient of K (depends on the
	 *                     recursion).
	 * @return Color at the given point
	 */
	private Color calcColor(GeoPoint intersection, Ray ray, int level, double k) {
		Color color = intersection.geometry.getEmission().add(calcLocalEffects(intersection, ray, k));
		return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray.getDir(), level, k));
	}

	/**
	 * Calculates the color at the given points, taking into account the reflection
	 * and refraction.
	 * 
	 * @param gp    - A point in the scene, which is on a geometric shape.
	 * @param v     - The ray from the camera that hit the above point.
	 * @param level - The current depth of the recursion.
	 * @param k     - The current coefficient of K (depends on the recursion).
	 * @return Color at the given point.
	 */
	private Color calcGlobalEffects(GeoPoint gp, Vector v, int level, double k) {
		Color color = Color.BLACK;
		Vector n = gp.geometry.getNormal(gp.point);
		Material material = gp.geometry.getMaterial();
		double kkr = k * material.kR;
		if (kkr > MIN_CALC_COLOR_K)
			color = calcGlobalEffect(constructReflectedRay(gp.point, v, n), level, material.kR, kkr);
		double kkt = k * material.kT;
		if (kkt > MIN_CALC_COLOR_K)
			color = color.add(calcGlobalEffect(constructRefractedRay(gp.point, v, n), level, material.kT, kkt));
		return color;
	}

	/**
	 * A function to continue the ray of the reflection the refraction, building a
	 * new one for the next level of the recursion.
	 * 
	 * @param ray   - The updated ray of the reflection and refraction.
	 * @param level - The current depth of the recursion.
	 * @param kx    - The original coefficient.
	 * @param kkx   - The current coefficient of K (depends on the recursion).
	 * @return
	 */
	private Color calcGlobalEffect(Ray ray, int level, double kx, double kkx) {
		GeoPoint intersections = findClosestIntersection(ray);
		return (intersections == null ? scene.background : calcColor(intersections, ray, level - 1, kkx).scale(kx));
	}

	/**
	 * Add calculated light contribution from all light sources.
	 * 
	 * @param intersection - A point in the scene, which is on a geometric shape
	 * @param ray          - The ray from the camera that hit the above point
	 * @return The color of the geometry according to the local variables, such as
	 *         the material and the light sources.
	 */
	private Color calcLocalEffects(GeoPoint intersection, Ray ray, double k) {

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
				double ktr = transparency(lightSource, l, n, intersection);
				if (ktr * k > MIN_CALC_COLOR_K) {
					Color lightIntensity = lightSource.getIntensity(p).scale(ktr);
					r = l.subtract(n.scale(2 * ln));
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
	private double transparency(LightSource light, Vector l, Vector n, GeoPoint geoPoint) {
		Vector lightDirection = l.scale(-1); // from point to light source
		Ray lightRay = new Ray(geoPoint.point, lightDirection, n);
		double lightDistance = light.getDistance(geoPoint.point);

		GeoPoint firstGP = findClosestIntersection(lightRay);

		if (firstGP == null)
			return 1.0;
		double ktr = 1.0;

		while (firstGP != null) {
			if (alignZero(firstGP.point.distance(geoPoint.point) - lightDistance) <= 0) {
				ktr *= firstGP.geometry.getMaterial().kT;
				if (ktr < MIN_CALC_COLOR_K)
					return 0.0;
			}
			firstGP = findClosestIntersection(new Ray(firstGP.point, lightRay.getDir()));
		}
		return ktr;
	}

	/**
	 * Creating a ray of the reflection.
	 * 
	 * @param p - The point of the reflection, the point we hit the object.
	 * @param v - The vector we hit the object at.
	 * @param n - The normal to the point hit.
	 * @return The new reflected ray.
	 */
	private Ray constructReflectedRay(Point3D p, Vector v, Vector n) {
		return new Ray(p, v.subtract(n.scale(2 * alignZero(v.dotProduct(n)))), n);
	}

	/**
	 * Creating a ray of the refrection.
	 * 
	 * @param p - The point of the refrection, the point we hit the object.
	 * @param v - The vector we hit the object at.
	 * @param n - The normal to the point hit.
	 * @return The new refracted ray.
	 */
	private Ray constructRefractedRay(Point3D p, Vector v, Vector n) {
		return new Ray(p, v, n);
	}

	@Override
	public Color traceRay(Ray ray) {
		GeoPoint intersections = findClosestIntersection(ray);
		return intersections == null ? scene.background : calcColor(intersections, ray);
	}

	/**
	 * Finding the closest intersections to the rays origin.
	 * 
	 * @param ray - The ray we will search to find where it lands.
	 * @return The closest GeoPoint in the rays path.
	 */
	private GeoPoint findClosestIntersection(Ray ray) {
		RayVoxels current = voxelsGrid.new RayVoxels(ray);
		if (current.mainRay == null)
			return null;
		
		if (current.voxelGeometries != null) {
			List<GeoPoint> intersections = current.voxelGeometries.findGeoIntersections(ray);
			GeoPoint firstIntersections = intersections == null ? null : ray.findClosestGeoPoint(intersections);
			if (firstIntersections != null && current.isInCurrentVoxel(firstIntersections.point))
				return firstIntersections;
		}
		
		while (current.nextVoxelGeometries()) {
				List<GeoPoint> intersections = current.voxelGeometries.findGeoIntersections(ray);
				GeoPoint firstIntersections = intersections == null ? null : ray.findClosestGeoPoint(intersections);
				if (firstIntersections != null && current.isInCurrentVoxel(firstIntersections.point))
					return firstIntersections;
		}
		
		return null;
	}
}
