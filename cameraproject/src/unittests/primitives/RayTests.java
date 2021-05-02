/**
 * 
 */
package unittests.primitives;

import static org.junit.Assert.*;

import java.util.List;
import java.util.ArrayList;

import org.junit.Test;

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

		// TC01:
		List<Point3D> pointsOnRayList = new ArrayList<Point3D>(3);
		pointsOnRayList.add(new Point3D(3, 0, 0));
		pointsOnRayList.add(new Point3D(1, 0, 0));
		pointsOnRayList.add(new Point3D(2, 0, 0));
		assertEquals("", new Point3D(1, 0, 0), ray.findClosestPoint(pointsOnRayList));

		// =============== Boundary Values Tests ==================

		// TC11:
		pointsOnRayList = new ArrayList<Point3D>(3);
		assertNull("", ray.findClosestPoint(pointsOnRayList));

		// TC12:
		pointsOnRayList = new ArrayList<Point3D>(3);
		pointsOnRayList.add(new Point3D(1, 0, 0));
		pointsOnRayList.add(new Point3D(3, 0, 0));
		pointsOnRayList.add(new Point3D(2, 0, 0));
		assertEquals("", new Point3D(1, 0, 0), ray.findClosestPoint(pointsOnRayList));

		// TC13:
		pointsOnRayList = new ArrayList<Point3D>(3);
		pointsOnRayList.add(new Point3D(2, 0, 0));
		pointsOnRayList.add(new Point3D(3, 0, 0));
		pointsOnRayList.add(new Point3D(1, 0, 0));
		assertEquals("", new Point3D(1, 0, 0), ray.findClosestPoint(pointsOnRayList));
	}

}
