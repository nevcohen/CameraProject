package geometries;

import primitives.Point3D;
import primitives.Vector;

public class Plane implements Geometry{
	private Point3D q0;
	private Vector normal;
	
	
	public Point3D getQ0() {
		return q0;
	}

	public Vector getNormal() {
		return normal;
	}

public Plane(Point3D point1,Point3D point2,Point3D point3) {
	Vector v1 = point1.subtract(point2);
	Vector v2 = point1.subtract(point3);
	q0 = point1;
	normal = v1.crossProduct(v2);
}

	public Plane(Point3D _q0, Vector _normal) {
	super();
	this.q0 = _q0;
	this.normal = _normal;
}

	@Override
	public Vector getNormal(Point3D point) {
		return null;
	}

	
	
}
