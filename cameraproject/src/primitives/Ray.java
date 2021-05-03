package primitives;

import static primitives.Util.*;

import java.util.List;

/**
 * Ray class - By using a vector and a starting point
 */
public class Ray {

	private Point3D p0;
	private Vector dir;

	/**
	 * Constructor getting normalized vector and a starting point
	 * 
	 * @param p0
	 * @param dir
	 */
	public Ray(Point3D p0, Vector dir) {
		this.p0 = p0;
		this.dir = dir.normalize();
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
		double minDis = p0.distanceSquared(pointsOnRay.get(0)), temp;
		Point3D closestPoint3d = pointsOnRay.get(0);
		for (Point3D point3d : pointsOnRay) {
			temp = p0.distanceSquared(point3d);
			if (temp < minDis) {
				minDis = temp;
				closestPoint3d = point3d;
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
