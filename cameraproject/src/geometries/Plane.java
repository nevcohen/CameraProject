package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * Plane class - represented by a point on it and its normal vector
 */
public class Plane implements Geometry{
	
	private Point3D q0;
	private Vector normal;
	
	/**
	 * @return q0 - the point that will help find the equation for the plane
	 */
	public Point3D getQ0() {
		return q0;
	}

	/**
	 * @return normal - the normal that will help find the equation for the plane
	 */
	public Vector getNormal() {
		return normal;
	}

	/**
	 * constructor that gets three points, and turns the information into a base point + normal format for a plane.
	 * @param Point3D - Three points on the plane
	 */
	public Plane(Point3D point1,Point3D point2,Point3D point3) {
		Vector v1 = point1.subtract(point2);
		Vector v2 = point1.subtract(point3);
		q0 = point1;
		normal = v1.crossProduct(v2);
	}

	/**
	 * constructor that gets a point, and a possible normal to the plane
	 * @param _q0 - A point on the plane
	 * @param _normal - The normal vector of the plane
	 */
	public Plane(Point3D _q0, Vector _normal) {
		super();
		this.q0 = _q0;
		this.normal = _normal;
	}

	@Override
	public Vector getNormal(Point3D point) {
		return null;
	}

	/**
	 * @return The plane as a Ax+By+Cz+D=0 format
	 */
	@Override
	public String toString() {
		Point3D head = normal.getHead();
		return head.getX_value()+"x +"+head.getY_value()+"y +"+head.getZ_value()+"z +"+(-normal.dotProduct(new Vector(q0))+" = 0");
	}
	
	
}