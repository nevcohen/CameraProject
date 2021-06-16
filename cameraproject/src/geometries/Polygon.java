package geometries;

import java.util.ArrayList;
import java.util.List;

import primitives.*;
import static primitives.Util.*;

/**
 * Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 * 
 * @author Dan
 */
public class Polygon extends Geometry {
	/**
	 * List of polygon's vertices
	 */
	protected List<Point3D> vertices;
	/**
	 * Associated plane in which the polygon lays
	 */
	protected Plane plane;

	/**
	 * Polygon constructor based on vertices list. The list must be ordered by edge
	 * path. The polygon must be convex.
	 * 
	 * @param vertices list of vertices according to their order by edge path
	 * @throws IllegalArgumentException in any case of illegal combination of
	 *                                  vertices:
	 *                                  <ul>
	 *                                  <li>Less than 3 vertices</li>
	 *                                  <li>Consequent vertices are in the same
	 *                                  point
	 *                                  <li>The vertices are not in the same
	 *                                  plane</li>
	 *                                  <li>The order of vertices is not according
	 *                                  to edge path</li>
	 *                                  <li>Three consequent vertices lay in the
	 *                                  same line (180&#176; angle between two
	 *                                  consequent edges)
	 *                                  <li>The polygon is concave (not convex)</li>
	 *                                  </ul>
	 */
	public Polygon(Point3D... vertices) {
		if (vertices.length < 3)
			throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
		this.vertices = List.of(vertices);
		// Generate the plane according to the first three vertices and associate the
		// polygon with this plane.
		// The plane holds the invariant normal (orthogonal unit) vector to the polygon
		plane = new Plane(vertices[0], vertices[1], vertices[2]);
		if (vertices.length == 3)
			return; // no need for more tests for a Triangle

		Vector n = plane.getNormal();

		// Subtracting any subsequent points will throw an IllegalArgumentException
		// because of Zero Vector if they are in the same point
		Vector edge1 = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
		Vector edge2 = vertices[0].subtract(vertices[vertices.length - 1]);

		// Cross Product of any subsequent edges will throw an IllegalArgumentException
		// because of Zero Vector if they connect three vertices that lay in the same
		// line.
		// Generate the direction of the polygon according to the angle between last and
		// first edge being less than 180 deg. It is hold by the sign of its dot product
		// with
		// the normal. If all the rest consequent edges will generate the same sign -
		// the
		// polygon is convex ("kamur" in Hebrew).
		boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
		for (int i = 1; i < vertices.length; ++i) {
			// Test that the point is in the same plane as calculated originally
			if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
				throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
			// Test the consequent edges have
			edge1 = edge2;
			edge2 = vertices[i].subtract(vertices[i - 1]);
			if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
				throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
		}
	}

	@Override
	public Vector getNormal(Point3D point) {
		return plane.getNormal();
	}
	/**
	 * getBoxMinMaxVertices takes the coordinates in the polygon, and extracts the data to create a cube
	 * around the polygon.
	 */

	@Override
	public List<GeoPoint> getBoxMinMaxVertices() {
		double minX = Double.POSITIVE_INFINITY, minY = Double.POSITIVE_INFINITY, minZ = Double.POSITIVE_INFINITY;
		double maxX = Double.NEGATIVE_INFINITY, maxY = Double.NEGATIVE_INFINITY, maxZ = Double.NEGATIVE_INFINITY;
		
		for (Point3D point : vertices) {
			double x = point.getValueOfX(), y = point.getValueOfY(), z = point.getValueOfZ();
			if (x < minX) minX = x;
			if (y < minY) minY = y;
			if (z < minZ) minZ = z;
			if (x > maxX) maxX = x;
			if (y > maxY) maxY = y;
			if (z > maxZ) maxZ = z;
		}

		return List.of(new GeoPoint(this, new Point3D(minX, minY, minZ)),
				new GeoPoint(this, new Point3D(maxX, maxY, maxZ)));
	}

	@Override
	public String toString() {
		return "Polygon [vertices=" + vertices + ", plane=" + plane + "]";
	}

	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
		List<GeoPoint> result = plane.findGeoIntersections(ray, maxDistance);
		if (result == null)
			return null;
		Point3D p0 = ray.getP0();

		List<Vector> allNi = new ArrayList<>(vertices.size());
		Vector v1, v2;
		for (int i = 0; i < (vertices.size() - 1); i++) {
			v1 = (vertices.get(i)).subtract(p0);
			v2 = (vertices.get(i + 1)).subtract(p0);
			allNi.add(v1.crossProduct(v2).normalize());
		}
		v1 = (vertices.get(vertices.size() - 1)).subtract(p0);
		v2 = (vertices.get(0)).subtract(p0);
		allNi.add(v1.crossProduct(v2).normalize());

		Vector rayV = ray.getDir();
		double vn1 = rayV.dotProduct(allNi.get(0));
		if (isZero(vn1))
			return null;
		for (Vector ni : allNi) {
			if (isZero(rayV.dotProduct(ni)) || !checkSign(vn1, rayV.dotProduct(ni))) // The point is inside if all
																						// v.dotProduct(Ni) have
				// the same sign or if one or more are
				// 0.0 – no intersection (The ray
				// intersects with the side of the
				// polygon)
				return null;
		}

		result.get(0).geometry = this;
		return result;
	}
}