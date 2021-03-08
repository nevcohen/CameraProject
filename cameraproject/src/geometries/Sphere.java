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
	 * @param _center - Sphere Center (Point3D)
	 * @param _radius - Sphere radius
	 */
	public Sphere(Point3D _center, double _radius) {
		super();
		this.center = _center;
		this.radius = _radius;
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
