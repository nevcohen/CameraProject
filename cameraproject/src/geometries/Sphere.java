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
			tm = alignZero(ray.getDir().dotProduct(u));
			SquaredD = u.lengthSquared() - tm * tm;
		}

		double SquaredR = radius * radius;
		if (SquaredD >= SquaredR) // there are no intersections
			return null;

		double th = radius;
		if (SquaredD != 0)
			th = Math.sqrt(SquaredR - SquaredD);

		double t1 = tm + th;
		double t2 = tm - th;

		if (t1 <= 0 && t2 <= 0)
			return null;
		List<Point3D> allIntersections = new ArrayList<Point3D>();
		if (t1 > 0)
			allIntersections.add(ray.getP0().add(ray.getDir().scale(t1)));
		if (t2 > 0)
			allIntersections.add(ray.getP0().add(ray.getDir().scale(t2)));
		return allIntersections;
	}
}
