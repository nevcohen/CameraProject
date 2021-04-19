package geometries;

import java.util.*;

import primitives.*;

/**
 * Interface for representing all geometric shapes that can be intersected with
 * a Ray
 */
public interface Intersectable {
<<<<<<< HEAD
=======
	/**
	 * Find all the points of intersection between Ray and the geometric shape
	 * 
	 * @param ray The Ray that is examined when it's intersected with the geometric
	 *            shape
	 * @return A list of all the points that the Ray intersects with the geometric
	 *         shape
	 */
>>>>>>> branch 'master' of https://github.com/nevcohen/CameraProject.git
	List<Point3D> findIntersections(Ray ray);
}
