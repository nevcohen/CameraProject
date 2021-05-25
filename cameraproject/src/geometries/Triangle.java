package geometries;

import static primitives.Util.*;

import java.util.List;

import primitives.*;

/**
 * Triangle class - Represented by the three vertices (3 Points3D)
 */
public class Triangle extends Polygon {

	/**
	 * Constructor for the triangle: puts 3 (and only 3) points into the Polygon
	 * base.
	 * 
	 * @param Point3D - The three vertices of the triangle
	 */
	public Triangle(Point3D point1, Point3D point2, Point3D point3) {
		super(point1, point2, point3);
	}

	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
		List<GeoPoint> result = plane.findGeoIntersections(ray, maxDistance);
		if (result == null)
			return null;
		Point3D p0 = ray.getP0();

		Vector v1, v2, v3, n1, n2;
		v1 = (vertices.get(0)).subtract(p0);
		v2 = (vertices.get(1)).subtract(p0);
		v3 = (vertices.get(2)).subtract(p0);
		n1 = v1.crossProduct(v2).normalize();
		n2 = v2.crossProduct(v3).normalize();

		// The point is inside if all rayV.dotProduct(vni) have the same sign or if one
		// or
		// more are 0.0 – no intersection (The ray intersects with the side of the
		// polygon)
		Vector rayV = ray.getDir();
		double vn1 = alignZero(rayV.dotProduct(n1));
		double vn2 = alignZero(rayV.dotProduct(n2));
		if (vn1 == 0 || vn2 == 0 || vn1*vn2 < 0)
			return null;

		n2 = v3.crossProduct(v1).normalize();
		vn2 = alignZero(rayV.dotProduct(n2));
		if (vn2 == 0 || vn1*vn2 < 0)
			return null;

		result.get(0).geometry = this;
		return result;
	}
}
