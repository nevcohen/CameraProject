package geometries;

import java.util.*;
import java.util.stream.Collectors;

import primitives.*;

/**
 * Interface for representing all geometric shapes that can be intersected with
 * a Ray
 */
public interface Intersectable {

	/**
	 * Class for representing a point on a geometric shape
	 */
	public static class GeoPoint {
		/**
		 * The geometric shape on which the point is located
		 */
		public Geometry geometry;
		/**
		 * The point itself on the geometric shape
		 */
		public Point3D point;

		/**
		 * A constructor who gets a point and the geometric shape on which the point is
		 * 
		 * @param geometry - The geometric shape on which the point
		 * @param point    - The point on the geometric shape
		 */
		public GeoPoint(Geometry geometry, Point3D point) {
			this.geometry = geometry;
			this.point = point;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			GeoPoint other = (GeoPoint) obj;
			return geometry == other.geometry && point.equals(other.point);
		}
	}

	/**
	 * Find all the points of intersection between Ray and the geometric shape
	 * 
	 * @param ray The Ray that is examined when it's intersected with the geometric
	 *            shape
	 * @return A list of all the points that the Ray intersects with the geometric
	 *         shape
	 */
	default List<Point3D> findIntersections(Ray ray) {
		var geoList = findGeoIntersections(ray);
		return geoList == null ? null : geoList.stream().map(gp -> gp.point).collect(Collectors.toList());
	}

	/**
	 * Find all the points of intersection between Ray and the geometric shape
	 * 
	 * @param ray - The Ray that is examined when it's intersected with the
	 *            geometric shape
	 * @return A list of GeoPoints of all the points that the Ray intersects with
	 *         the geometric shape
	 */
	default List<GeoPoint> findGeoIntersections(Ray ray) {
		return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
	}

	/**
	 * --------------------
	 * 
	 * @return
	 */
	List<GeoPoint> getBoxMinMaxVertices();

	/**
	 * Find all the points of intersection between Ray and the geometric shape,
	 * within a maximum range from the beginning of the ray.
	 * 
	 * @param ray         - The Ray that is examined when it's intersected with the
	 *                    geometric shape
	 * @param maxDistance - The maximum distance
	 * @return A list of GeoPoints of all the points that the Ray intersects with
	 *         the geometric shape
	 */
	List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance);
}
