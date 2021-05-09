package geometries;

import primitives.*;

/**
 * Interface for representing all geometric shapes
 */
public abstract class Geometry implements Intersectable {

	/**
	 * -------------------
	 */
	protected Color emission = Color.BLACK;

	/**
	 * -------------------
	 * @return
	 */
	public Color getEmission() {
		return emission;
	}

	/**
	 * --------------------
	 * @param emmission
	 * @return
	 */
	public Geometry setEmission(Color emission) {
		this.emission = emission;
		return this;
	}

	/**
	 * Get the normal to the surface
	 * 
	 * @param point - A point3D on the geometric body
	 * @return A normal vector to the body at this point
	 */
	abstract public Vector getNormal(Point3D point);
}