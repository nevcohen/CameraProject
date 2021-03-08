package geometries;

import primitives.Point3D;
import primitives.Vector;

public class Sphere implements Geometry {

	private Point3D center;
	private double radius;
	
	
	
	public Sphere(Point3D _center, double _radius) { // Ctor that gets the center of the sphere and a radius, and inputs the fields.
		super();
		this.center = _center;
		this.radius = _radius;
	}

	public Point3D getCenter() {// returns the center point for the sphere.
		return center;
	}

	public double getRadius() {// returns the value of the radius of the sphere.
		return radius;
	}

	@Override
	public Vector getNormal(Point3D point) {
		return null;
	}

	@Override
	public String toString() { // returns the center and radius of the sphere in the classic tostring format.
		return "Sphere [center=" + center + ", radius=" + radius + "]";
	}
	
	

}
