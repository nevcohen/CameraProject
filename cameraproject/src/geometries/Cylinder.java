package geometries;

import static primitives.Util.alignZero;

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
	 * Constructor getting the ray and radius for the tube, and the height for the
	 * cylinder.
	 * 
	 * @param axisRay Direction and starting point of the Cylinder
	 * @param radius  Cylinder radius
	 * @param height  Cylinder height
	 */
	public Cylinder(Ray axisRay, double radius, double height) {
		super(axisRay, radius);
		this.height = height;
	}

	/**
	 * Gets the height value
	 * 
	 * @return the height of the cylinder
	 */
	public double getHeight() {
		return height;
	}

	@Override
	public Vector getNormal(Point3D point) {
		Point3D p0 = axisRay.getP0();
		if (p0.equals(point))
			return axisRay.getDir().scale(-1);
		Vector v = point.subtract(p0);
		double scaleBy = alignZero(axisRay.getDir().dotProduct(v)); // The projection of V on the Ray
		if (scaleBy == 0) // point is on the bottom base (beginning of the Ray)
			return axisRay.getDir().scale(-1);
		Point3D o = axisRay.getPoint(scaleBy); // The point on the Ray that is opposite to
												// point
		double d = p0.subtract(o).lengthSquared();
		if (d == height * height) // point is on the top base
			return axisRay.getDir();
		return point.subtract(o).normalize();
	}

	/**
	 * @return The string format of the Cylinder.
	 */
	@Override
	public String toString() {
		return super.toString() + " Cylinder [height=" + height + "]";
	}
}
