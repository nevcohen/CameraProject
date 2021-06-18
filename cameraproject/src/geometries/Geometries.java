/**
 * 
 */
package geometries;

import java.util.LinkedList;
import java.util.List;

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

	@Override
	public List<GeoPoint> getBoxMinMaxVertices() {
		if (allGeometries == null)
			throw new IllegalArgumentException("The list of geometric shapes is empty");

		List<GeoPoint> allVertices = new LinkedList<GeoPoint>();
		for (Intersectable intersectable : allGeometries)
			allVertices.addAll(intersectable.getBoxMinMaxVertices());

		return allVertices;
	}

	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {

		if (allGeometries == null)
			return null;
		List<GeoPoint> allIntersectables = null;
		for (Intersectable current : allGeometries) {
			List<GeoPoint> currentIntersections = current.findGeoIntersections(ray, maxDistance);
			if (currentIntersections != null) {
				if (allIntersectables == null)
					allIntersectables = new LinkedList<GeoPoint>(currentIntersections);
				else
					allIntersectables.addAll(currentIntersections);
			}
		}
		return allIntersectables;
	}

	/**
	 * 
	 * @param ray
	 * @param maxDistance
	 * @param rayID
	 * @return
	 */
	public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance, int rayID) {
		if (allGeometries == null)
			return null;
		List<GeoPoint> allIntersectables = null;
		for (Intersectable current : allGeometries) {
			if (((Geometry) current).rayID != rayID) {
				List<GeoPoint> currentIntersections = current.findGeoIntersections(ray, maxDistance);
				if (currentIntersections != null) {
					((Geometry) current).rayID = rayID;
					if (allIntersectables == null)
						allIntersectables = new LinkedList<GeoPoint>(currentIntersections);
					else
						allIntersectables.addAll(currentIntersections);
				}
			}
		}
		return allIntersectables;
	}

}
