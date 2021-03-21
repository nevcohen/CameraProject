package geometries;

import primitives.*;

/**
 * Tube class - Represented by the ray and radius of the Tube
 */
public class Tube implements Geometry{

	protected Ray axisRay;
	protected double radius;
	
	/**
	 * Ctor getting the ray and radius for the tube.
	 * @param axisRay Direction and starting point of the Tube
	 * @param radius Tube radius
	 */
	public Tube(Ray axisRay, double radius) {
		this.axisRay = axisRay;
		this.radius = radius;
	}

	/**
	 * returns the ray of the tube
	 */
	public Ray getAxisRay() {
		return axisRay;
	}

	/**
	 * returns the radius of the tube
	 */
	public double getRadius() {
		return radius;
	}


	@Override
	public Vector getNormal(Point3D point) {
		
		double scaleBy = axisRay.getDir().dotProduct(point.subtract(axisRay.getP0()));
		if(scaleBy == 0)
			return point.subtract(axisRay.getP0()).normalize();
		Point3D o = axisRay.getP0().add(axisRay.getDir().scale(scaleBy));
		return point.subtract(o).normalize();
	}

	/**
	 * @return The ray and radius of the tube in the classic tostring format.
	 */
	@Override
	public String toString() {
		return "Tube [axisRay=" + axisRay + ", radius=" + radius + "]";
	}

	
	
}
