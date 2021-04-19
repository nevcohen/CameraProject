/**
 * 
 */
package geometries;

import java.util.LinkedList;
import java.util.List;

import primitives.Point3D;
import primitives.Ray;

/**
*
*/
public class Geometries implements Intersectable {

	/**
	 * ArrayList of all existing geometries
	 * 
	 * ArrayList or LinkedList? When going through the collection we will have to go
	 * one by one and not have to go from the middle, so using linked lists is more
	 * efficient than array lists. We also want more dynamism if there are few
	 * geometries and do not take up much memory space
	 */
	private List<Intersectable> allGeometries;

	/**
	 * Default constructor - Creates an empty collection
	 */
	public Geometries() {
		allGeometries = new LinkedList<Intersectable>();
	}

	/**
	 * A constructor who creates a new list with geometries
	 * 
	 * @param geometries A collection of new geometries
	 */
	public Geometries(Intersectable... geometries) {
		allGeometries = new LinkedList<Intersectable>();
		for (Intersectable current : geometries) {
			allGeometries.add(current);
		}
	}

	/**
	 * Adding new geometries to the list
	 * 
	 * @param geometries A collection of new geometries to add
	 */
	public void add(Intersectable... geometries) {
		for (Intersectable current : geometries) {
			allGeometries.add(current);
		}
	}

	/**
	 * Implementation of a structural pattern - Composite, Finding all the
	 * intersections between the Ray we got for all the geometries in the collection
	 * 
	 * @return A list of all the points that the Ray intersects with them
	 */
	@Override
	public List<Point3D> findIntersections(Ray ray) {

		if (allGeometries.isEmpty())
			return null;
		List<Point3D> allIntersectables = new LinkedList<Point3D>();
		for (Intersectable current : allGeometries) {
			List<Point3D> currentIntersections = current.findIntersections(ray);
			if (!currentIntersections.isEmpty())
				allIntersectables.addAll(currentIntersections);
		}
		if (allIntersectables.isEmpty())
			return null;
		return allIntersectables;
	}

}
