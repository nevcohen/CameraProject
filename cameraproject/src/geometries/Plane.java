package geometries;

import primitives.Point3D;
import primitives.Vector;

public class Plane implements Geometry{
	
	private Point3D q0;
	private Vector normal;
	
	
	public Point3D getQ0() { // get the point that will help find the equation for the plane
		return q0;
	}

	public Vector getNormal() { // get the normal that will help find the equation for the plane
		return normal;
	}

	public Plane(Point3D point1,Point3D point2,Point3D point3) { // constructor that gets three points, and turns the information into a base point + normal format for a plane.
	Vector v1 = point1.subtract(point2);
	Vector v2 = point1.subtract(point3);
	q0 = point1;
	normal = v1.crossProduct(v2);
	}

	public Plane(Point3D _q0, Vector _normal) { // constructor that gets a point, and a possible normal to the plane
	super();
	this.q0 = _q0;
	this.normal = _normal;
}

	@Override
	public Vector getNormal(Point3D point) {
		return null;
	}

	@Override
	public String toString() { // tostring for a plane returns the plane as a Ax+By+Cz+D=0 format
		Point3D head = normal.getHead();
		return head.getX_value()+"x +"+head.getY_value()+"y +"+head.getZ_value()+"z +"+(-normal.dotProduct(new Vector(q0))+" = 0");
	}
	
	
}
