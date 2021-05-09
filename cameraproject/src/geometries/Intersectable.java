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
	 * ----------------
	 */
	public static class GeoPoint {
		/**
		 * 
		 */
		public Geometry geometry;
		/**
		 * 
		 */
		public Point3D point;

		/**
		 * @param geometry
		 * @param point
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
	 * @param ray The Ray that is examined when it's intersected with the geometric
	 *            shape
	 * @return A list of GeoPoints of all the points that the Ray intersects with the geometric
	 *         shape
	 */
	List<GeoPoint> findGeoIntersections(Ray ray);
}
