package geometries;

import primitives.*;

/**
 * a class for a tube with a certain height (tube is inf. length)
 */
public class Cylinder extends Tube {
	
	/**
	 * Cylinder height
	 */
	private double height;
	
	/**
	 * Ctor getting the ray and radius for the tube, and the height for the cylinder.
	 * @param _axisRay Direction and starting point of the Cylinder
	 * @param _radius Cylinder radius
	 * @param _height Cylinder height
	 */
	public Cylinder(Ray _axisRay, double _radius, double _height) {
		super(_axisRay, _radius);
		this.height = _height;
	}
	
	/**
	 * returns the height of the cylinder
	 */
	public double getHeight() {
		return height;
	}

	@Override
	public Vector getNormal(Point3D point) { 
		return null;
	}

	/**
	 * @return The string format of the Cylinder.
	 */
	@Override
	public String toString() {
		return super.toString()+" Cylinder [height=" + height + "]";
	}	
}
