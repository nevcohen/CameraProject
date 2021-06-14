package geometries;

import java.util.List;
import static primitives.Util.*;
import primitives.*;

/**
 * Tube class - Represented by the ray and radius of the Tube
 */
public class Tube extends Geometry {

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
		double scaleBy = alignZero(axisRay.getDir().dotProduct(v)); // The projection of V on the Ray
		if (scaleBy == 0) // The point is opposite the beginning of the Ray
			return v.normalize();
		Point3D o = axisRay.getPoint(scaleBy); // The point on the Ray that is opposite to
												// point
		return point.subtract(o).normalize();
	}
	
	@Override
	public List<GeoPoint> getBoxMinMaxVertices() {
		return null;
	}

	/**
	 * @return The ray and radius of the tube in the classic tostring format.
	 */
	@Override
	public String toString() {
		return "Tube [axisRay=" + axisRay + ", radius=" + radius + "]";
	}

	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
		return null;
	}
}
