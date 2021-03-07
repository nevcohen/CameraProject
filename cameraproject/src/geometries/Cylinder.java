package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class Cylinder extends Tube {
	

	private double height;
	
	public Cylinder(Ray _axisRay, double _radius, double _height) {
		super(_axisRay, _radius);
		this.height = _height;
	}
	
	public double getHeight() {
		return height;
	}

	@Override
	public Vector getNormal(Point3D point) {
		return null;

	}
}
