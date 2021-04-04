package geometries;

import java.util.List;

import primitives.*;

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
	public List<Point3D> findIntsersections(Ray ray) {
		// TODO Auto-generated method stub
		return null;
	}
}
