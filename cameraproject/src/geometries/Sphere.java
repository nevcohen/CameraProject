package geometries;

import primitives.*;

/**
 * Sphere class - represented by the center point and radius
 */
public class Sphere implements Geometry {

	private Point3D center;
	private double radius;
		
	/**
	 * Ctor that gets the center of the sphere and a radius, and inputs the fields.
	 * @param center - Sphere Center (Point3D)
	 * @param radius - Sphere radius
	 */
	public Sphere(Point3D center, double radius) {
		super();
		this.center = center;
		this.radius = radius;
	}

	/**
	 * returns the center point for the sphere.
	 */
	public Point3D getCenter() {
		return center;
	}

	/**
	 * returns the value of the radius of the sphere.
	 */
	public double getRadius() {
		return radius;
	}

	@Override
	public Vector getNormal(Point3D point) {
		return null;
	}

	/**
	 * @return The center and radius of the sphere in the classic tostring format.
	 */
	@Override
	public String toString() {
		return "Sphere [center=" + center + ", radius=" + radius + "]";
	}
	
	

}
