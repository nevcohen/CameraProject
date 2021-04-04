package geometries;

import primitives.*;

/**
 * Interface for representing all geometric shapes
 */
public interface Geometry extends Intersectable {

	/**
	 * Get the normal to the surface
	 * @param point - A point3D on the geometric body
	 * @return A normal vector to the body at this point
	 */
	public Vector getNormal(Point3D point);
}
