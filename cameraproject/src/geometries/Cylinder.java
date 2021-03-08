package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class Cylinder extends Tube { // a class for a tube with a certain height (tube is inf. length)
	

	private double height;
	
	public Cylinder(Ray _axisRay, double _radius, double _height) {//Ctor getting the ray and radius for the tube, and the height for the cylinder.
		super(_axisRay, _radius);
		this.height = _height;
	}
	
	public double getHeight() {//returns the height of the cylinder
		return height;
	}

	@Override
	public Vector getNormal(Point3D point) { 
		return null;

	}

	@Override
	public String toString() { // returns the string format of the Cylinder.
		return super.toString()+" Cylinder [height=" + height + "]";
	}
	
	
	
}
