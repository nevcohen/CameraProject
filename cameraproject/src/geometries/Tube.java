package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class Tube implements Geometry{

	protected Ray axisRay;
	protected double radius;
	
	
	public Tube(Ray _axisRay, double _radius) {
		super();
		this.axisRay = _axisRay;
		this.radius = _radius;
	}


	public Ray getAxisRay() {
		return axisRay;
	}


	public double getRadius() {
		return radius;
	}


	@Override
	public Vector getNormal(Point3D point) {
		// TODO Auto-generated method stub
		return null;
	}

}
