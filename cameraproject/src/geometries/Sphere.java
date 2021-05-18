package geometries;

import java.util.*;
import static primitives.Util.*;

import primitives.Vector;
import primitives.Point3D;
import primitives.Ray;

/**
 * Sphere class - represented by the center point and radius
 */
public class Sphere extends Geometry {

	private Point3D center;
	private double radius;

	/**
	 * Constructor that gets the center of the sphere and a radius, and inputs the
	 * fields.
	 * 
	 * @param center - Sphere Center (Point3D)
	 * @param radius - Sphere radius
	 */
	public Sphere(Point3D center, double radius) {
		this.center = center;
		this.radius = radius;
	}

	/**
	 * Gets center value
	 * 
	 * @return the center point for the sphere.
	 */
	public Point3D getCenter() {
		return center;
	}

	/**
	 * Gets radius value
	 * 
	 * @return the value of the radius of the sphere.
	 */
	public double getRadius() {
		return radius;
	}

	@Override
	public Vector getNormal(Point3D point) {
		return point.subtract(this.center).normalize();
	}

	/**
	 * @return The center and radius of the sphere in the classic tostring format.
	 */
	@Override
	public String toString() {
		return "Sphere [center=" + center + ", radius=" + radius + "]";
	}

	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray) {

		double tm = 0;
		double SquaredD = 0;
		try {
			Vector u = center.subtract(ray.getP0());
			tm = alignZero(ray.getDir().dotProduct(u)); // The projection of vector u on v
			SquaredD = u.lengthSquared() - tm * tm; // The minimum distance between the center of the Sphere and the Ray
													// (Squared)
		} catch (IllegalArgumentException e) {
			return List.of(new GeoPoint(this, ray.getPoint(radius)));
		}

		double SquaredR = radius * radius;
		double diff = alignZero(SquaredR - SquaredD);
		if (diff <= 0) // There are no intersections
			return null;

		// The distance from the point of intersection to the closest point to the
		// center of the circle that on the Ray
		double th = Math.sqrt(diff);

		double t2 = alignZero(tm + th); // The second point of intersection
		// When t1 or t2 is less than zero the points of intersection is before the ray
		if (t2 <= 0)
			return null;

		double t1 = alignZero(tm - th); // The first point of intersection
		return t1 > 0 //
				? List.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2))) //
				: List.of(new GeoPoint(this, ray.getPoint(t2)));
	}
}
