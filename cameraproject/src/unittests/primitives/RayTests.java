/**
 * 
 */
package unittests.primitives;

import static org.junit.Assert.*;

import java.util.List;
import java.util.ArrayList;

import org.junit.Test;

import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import geometries.Intersectable.GeoPoint;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * Tests for Ray class
 */
public class RayTests {

	/**
	 * Test method for {@link primitives.Ray#findClosestPoint(java.util.List)}.
	 */
	@Test
	public void testFindClosestPoint() {
		Ray ray = new Ray(Point3D.ZERO, new Vector(new Point3D(1, 0, 0)));
		// ============ Equivalence Partitions Tests ==============

		// TC01: A point in the middle of the list is closest to the beginning of the
		// Ray
		List<Point3D> pointsOnRayList = new ArrayList<Point3D>(3);
		pointsOnRayList.add(new Point3D(3, 0, 0));
		pointsOnRayList.add(new Point3D(1, 0, 0));
		pointsOnRayList.add(new Point3D(2, 0, 0));
		assertEquals("findClosestPoint() point in the middle of the list is closest", new Point3D(1, 0, 0),
				ray.findClosestPoint(pointsOnRayList));

		// =============== Boundary Values Tests ==================

		// TC11: An empty list
		pointsOnRayList = new ArrayList<Point3D>(3);
		assertNull("findClosestPoint() empty list", ray.findClosestPoint(pointsOnRayList));

		// TC12: The first point in the list is closest to the beginning of the Ray
		pointsOnRayList = new ArrayList<Point3D>(3);
		pointsOnRayList.add(new Point3D(1, 0, 0));
		pointsOnRayList.add(new Point3D(3, 0, 0));
		pointsOnRayList.add(new Point3D(2, 0, 0));
		assertEquals("findClosestPoint() first point in the list is closest", new Point3D(1, 0, 0),
				ray.findClosestPoint(pointsOnRayList));

		// TC13: The last point in the list is closest to the beginning of the Ray
		pointsOnRayList = new ArrayList<Point3D>(3);
		pointsOnRayList.add(new Point3D(2, 0, 0));
		pointsOnRayList.add(new Point3D(3, 0, 0));
		pointsOnRayList.add(new Point3D(1, 0, 0));
		assertEquals("findClosestPoint() last point in the list is closest", new Point3D(1, 0, 0),
				ray.findClosestPoint(pointsOnRayList));
	}

	/**
	 * Test method for {@link primitives.Ray#findClosestGeoPoint(java.util.List)}.
	 */
	@Test
	public void testFindClosestGeoPoint() {
		Ray ray = new Ray(Point3D.ZERO, new Vector(new Point3D(1, 0, 0)));
		Plane plane = new Plane(new Point3D(2, 0, 0), new Vector(new Point3D(1, 0, 0)));
		Sphere sphere = new Sphere(new Point3D(-1, 0, 0), 2);
		Triangle triangle = new Triangle(new Point3D(3, 0, 2), new Point3D(3, 1, -1), new Point3D(3, -1, -1));
		GeoPoint geoPoint = new GeoPoint(sphere, new Point3D(1, 0, 0));
		// ============ Equivalence Partitions Tests ==============

		// TC01: A point in the middle of the list is closest to the beginning of the
		// Ray
		List<GeoPoint> pointsOnRayList = new ArrayList<GeoPoint>(3);
		pointsOnRayList.add(new GeoPoint(triangle, new Point3D(3, 0, 0)));
		pointsOnRayList.add(new GeoPoint(sphere, new Point3D(1, 0, 0)));
		pointsOnRayList.add(new GeoPoint(plane, new Point3D(2, 0, 0)));
		assertEquals("findClosestGeoPoint() point in the middle of the list is closest", geoPoint,
				ray.findClosestGeoPoint(pointsOnRayList));

		// =============== Boundary Values Tests ==================

		// TC11: An empty list
		pointsOnRayList = new ArrayList<GeoPoint>(3);
		assertNull("findClosestGeoPoint() empty list", ray.findClosestGeoPoint(pointsOnRayList));

		// TC12: The first point in the list is closest to the beginning of the Ray
		pointsOnRayList = new ArrayList<GeoPoint>(3);		
		pointsOnRayList.add(new GeoPoint(sphere, new Point3D(1, 0, 0)));
		pointsOnRayList.add(new GeoPoint(triangle, new Point3D(3, 0, 0)));
		pointsOnRayList.add(new GeoPoint(plane, new Point3D(2, 0, 0)));
		assertEquals("findClosestGeoPoint() first point in the list is closest", geoPoint,
				ray.findClosestGeoPoint(pointsOnRayList));

		// TC13: The last point in the list is closest to the beginning of the Ray
		pointsOnRayList = new ArrayList<GeoPoint>(3);
		pointsOnRayList.add(new GeoPoint(plane, new Point3D(2, 0, 0)));
		pointsOnRayList.add(new GeoPoint(triangle, new Point3D(3, 0, 0)));
		pointsOnRayList.add(new GeoPoint(sphere, new Point3D(1, 0, 0)));		
		assertEquals("findClosestGeoPoint() last point in the list is closest", geoPoint,
				ray.findClosestGeoPoint(pointsOnRayList));
	}

}
