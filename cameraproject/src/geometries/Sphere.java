package geometries;

import java.util.*;
import static primitives.Util.*;

import primitives.Vector;
import primitives.Point3D;
import primitives.Ray;

/**
 * Sphere class - represented by the center point and radius
 */
public class Sphere implements Geometry {

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
	public List<Point3D> findIntersections(Ray ray) {
		Vector u = null;
		if (!center.equals(ray.getP0()))
			u = center.subtract(ray.getP0());

		double tm = 0;
		double SquaredD = 0;
		if (u != null) {
			tm = alignZero(ray.getDir().dotProduct(u)); // The projection of vector u on v
			SquaredD = u.lengthSquared() - tm * tm; // The minimum distance between the center of the Sphere and the Ray
													// (Squared)
		}

		double SquaredR = radius * radius;
		if (SquaredD >= SquaredR) // There are no intersections
			return null;

		double th = radius; // The distance from the point of intersection to the closest point to the
							// center of the circle that on the Ray
		if (SquaredD != 0)
			th = Math.sqrt(SquaredR - SquaredD);

		double t1 = tm - th; // The first point of intersection
		double t2 = tm + th; // The second point of intersection

		// When t1 or t2 is less than zero the points of intersection is before the ray
		if (t1 <= 0 && t2 <= 0)
			return null;
		List<Point3D> allIntersections = new LinkedList<Point3D>();
		if (t1 > 0)
			allIntersections.add(ray.getPoint(t1));
		if (t2 > 0)
			allIntersections.add(ray.getPoint(t2));
		return allIntersections;
	}
}
