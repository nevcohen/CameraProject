package geometries;

import primitives.*;

/**
 * Interface for representing all geometric shapes
 */
public abstract class Geometry implements Intersectable {

	/**
	 * The color of the geometric shape, default black color.
	 */
	protected Color emission = Color.BLACK;

	/**
	 * Getter for the color of the geometric shape
	 * 
	 * @return emission of the specific geometry
	 */
	public Color getEmission() {
		return emission;
	}

	/**
	 * Setter for the color of the geometric shape (builder)
	 * 
	 * @param emmission of the specific geometry
	 * @return The object itself (the geometric shape)
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