package primitives;

import static primitives.Util.*;

import java.util.List;
import java.util.stream.Collectors;

import geometries.Intersectable.GeoPoint;

/**
 * Ray class - By using a vector and a starting point
 */
public class Ray {

	/**
	 * The margin of error - space we will use to calculate the reflection and
	 * refraction.
	 */
	private static final double DELTA = 0.1;

	/**
	 * The head of the ray
	 */
	private Point3D p0;
	/**
	 * The direction of the ray
	 */
	private Vector dir;

	/**
	 * Constructor getting normalized vector and a starting point.
	 * 
	 * @param p0  - The head of the ray.
	 * @param dir - The direction of the ray.
	 */
	public Ray(Point3D p0, Vector dir) {
		this.p0 = p0;
		this.dir = dir.normalize();
	}

	/**
	 * Constuctor that gets a point and a vector to create a ray, but also
	 * recalculates the ray according to a normal, to add the margin of error DELTA.
	 * 
	 * @param head      - The head of the old ray.
	 * @param direction - The direction of the ray, must be normalize.
	 * @param normal    - The normal of head that we wish to add DELTA to.
	 */
	public Ray(Point3D head, Vector direction, Vector normal) {
		double dn = alignZero(direction.dotProduct(normal));
		p0 = dn == 0 ? head : head.add(normal.scale(dn > 0 ? DELTA : -DELTA));
		dir = direction;
	}

	/**
	 * Get the head point of the Ray
	 * 
	 * @return starting point
	 */
	public Point3D getP0() {
		return p0;
	}

	/**
	 * Get direction of the Ray
	 * 
	 * @return normalized vector
	 */
	public Vector getDir() {
		return dir;
	}

	/**
	 * Calculation of a point on a Ray
	 * 
	 * @param t Scalar
	 * @return Point on the Ray
	 */
	public Point3D getPoint(double t) {
		if (isZero(t))
			return p0;
		return p0.add(dir.scale(t));
	}

	/**
	 * Finding the closest point to the head of the Ray from a list of points given
	 * on the Ray, which are points of intersection with different geometric bodies.
	 * 
	 * @param pointsOnRay List of points on the Ray
	 * @return The closest point to the head of the Ray
	 */
	public Point3D findClosestPoint(List<Point3D> pointsOnRay) {
		if (pointsOnRay.isEmpty())
			return null;

		return findClosestGeoPoint(
				pointsOnRay.stream().map(p -> new GeoPoint(null, p)).collect(Collectors.toList())).point;
	}

	/**
	 * Finding the closest point to the head of the Ray from a list of points given
	 * on the Ray, which are GeoPoints of intersection with different geometric
	 * bodies.
	 * 
	 * @param pointsOnRay List of GeoPoints on the Ray
	 * @return The closest GeoPoint to the head of the Ray
	 */
	public GeoPoint findClosestGeoPoint(List<GeoPoint> pointsOnRay) {
		if (pointsOnRay == null)
			return null;
		double minDis = Double.POSITIVE_INFINITY;
		GeoPoint closestPoint3d = null;
		for (GeoPoint geoPoint : pointsOnRay) {
			double temp = p0.distanceSquared(geoPoint.point);
			if (temp < minDis) {
				minDis = temp;
				closestPoint3d = geoPoint;
			}
		}

		return closestPoint3d;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Ray))
			return false;
		Ray other = (Ray) obj;
		return this.p0.equals(other.p0) && this.dir.equals(other.dir);

	}

	/**
	 * The string format of the Ray, in format -> t(x,y,z) + (u,v,w)
	 */
	@Override
	public String toString() {
		return "t" + dir + " + " + p0;
	}

}
