package geometries;

import primitives.*;

/**
 * Interface for representing all geometric shapes
 */
public abstract class Geometry implements Intersectable {

	/**
	 * The color of the geometric shape, default black color.
	 */
	private Color emission = Color.BLACK;

	/**
	 * -----------------
	 */
	private Material material = new Material();

	/**
	 * Getter for the color of the geometric shape
	 * 
	 * @return emission of the specific geometry
	 */
	public Color getEmission() {
		return emission;
	}

	/**
	 * -----------------------
	 * 
	 * @return
	 */
	public Material getMaterial() {
		return material;
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
	 * ---------------------------
	 * 
	 * @param material
	 * @return
	 */
	public Geometry setMaterial(Material material) {
		this.material = material;
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