package geometries;

import java.util.List;
import static primitives.Util.*;
import primitives.*;

/**
 * Tube class - Represented by the ray and radius of the Tube
 */
public class Tube implements Geometry {

	protected Ray axisRay;
	protected double radius;

	/**
	 * Constructor getting the ray and radius for the tube.
	 * 
	 * @param axisRay Direction and starting point of the Tube
	 * @param radius  Tube radius
	 */
	public Tube(Ray axisRay, double radius) {
		this.axisRay = axisRay;
		this.radius = radius;
	}

	/**
	 * Gets the Ray value
	 * 
	 * @return the ray of the tube
	 */
	public Ray getAxisRay() {
		return axisRay;
	}

	/**
	 * Gets the radius value
	 * 
	 * @return the radius of the tube
	 */
	public double getRadius() {
		return radius;
	}

	@Override
	public Vector getNormal(Point3D point) {
		Vector v = point.subtract(axisRay.getP0());
		double scaleBy = alignZero(axisRay.getDir().dotProduct(v));
		if (scaleBy == 0)
			return v.normalize();
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

	@Override
	public List<Point3D> findIntersections(Ray ray) {
		// TODO Auto-generated method stub
		return null;
	}
}
