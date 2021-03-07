package geometries;

import primitives.Point3D;
import primitives.Vector;

public class Sphere implements Geometry {

	private Point3D center;
	private double radius;
	
	
	
	public Sphere(Point3D _center, double _radius) {
		super();
		this.center = _center;
		this.radius = _radius;
	}

	public Point3D getCenter() {
		return center;
	}

	public double getRadius() {
		return radius;
	}

	@Override
	public Vector getNormal(Point3D point) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		return "Sphere [center=" + center + ", radius=" + radius + "]";
	}
	
	

}
