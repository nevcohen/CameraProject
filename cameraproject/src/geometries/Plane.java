package geometries;

import java.util.List;

import primitives.*;

/**
 * Plane class - represented by a point on it and its normal vector
 */
public class Plane implements Geometry {

	private Point3D q0;
	private Vector normal;

	/**
	 * Gets q0 value
	 * 
	 * @return q0 - the point that will help find the equation for the plane
	 */
	public Point3D getQ0() {
		return q0;
	}

	/**
	 * Gets the normal value
	 * 
	 * @return normal - the normal that will help find the equation for the plane
	 */
	public Vector getNormal() {
		return normal;
	}

	/**
	 * constructor that gets three points, and turns the information into a base
	 * point + normal format for a plane.
	 * 
	 * @param Point3D - Three points on the plane
	 */
	public Plane(Point3D point1, Point3D point2, Point3D point3) {
		Vector v1 = point1.subtract(point2);
		Vector v2 = point1.subtract(point3);
		q0 = point1;
		normal = v1.crossProduct(v2).normalize();
	}

	/**
	 * constructor that gets a point, and a possible normal to the plane
	 * 
	 * @param q0     - A point on the plane
	 * @param normal - The normal vector of the plane
	 */
	public Plane(Point3D q0, Vector normal) {
		this.q0 = q0;
		this.normal = normal.normalize();
	}

	@Override
	public Vector getNormal(Point3D point) {
		return this.normal;
	}

	/**
	 * @return The plane as a Ax+By+Cz+D=0 format
	 */
	@Override
	public String toString() {
		String[] xyz = normal.getHead().toString().split(", "); // (x, y, z)
		return xyz[0].substring(1) + "x +" + xyz[1] + "y +" + xyz[0].substring(0, -1) + "z +"
				+ (-normal.dotProduct(new Vector(q0)) + " = 0");
	}

	@Override
	public List<Point3D> findIntsersections(Ray ray) {
		// TODO Auto-generated method stub
		return null;
	}
}
