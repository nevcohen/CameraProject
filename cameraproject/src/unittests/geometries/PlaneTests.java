package unittests.geometries;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import geometries.Plane;
import primitives.*;

/**
 * Testing Planes
 */
public class PlaneTests {

	/**
	 * Test method for {@link geometries.Plane#getNormal(primitives.Point3D)}.
	 */
	@Test
	public void testGetNormalPoint3D() {
		Plane p = new Plane(new Point3D(1, 1, 1), new Vector(1, 2, 3));
		// ============ Equivalence Partitions Tests ==============

		// TC01: There is a simple single test here
		assertEquals("getNormal() Mistake found with GetNormal", p.getNormal(new Point3D(7, -2, 1)),
				new Vector(1, 2, 3).normalize());
	}

	/**
	 * Test method for {@link geometries.Plane#findIntersections(primitives.Ray)}.
	 */
	@Test
	public void findIntersections() {
		Plane p = new Plane(new Point3D(0, 0, 0), new Vector(0, 0, 1));
		List<Point3D> expected;

		// EP01:Ray intersects with plane
		expected = p.findIntersections(new Ray(new Point3D(-1, -1, -1), new Vector(1, -1, 1)));
		assertEquals("Wrong number of points", 1, expected.size());
		assertEquals("Wrong points expected", List.of(new Point3D(0, -2, 0)), expected);

		// EP02: Ray does not intersect with the plane
		expected = p.findIntersections(new Ray(new Point3D(0, 0, 1), new Vector(1, 0, 0)));
		assertNull("Wrong number of points", expected);

		// BVA01: Ray is parallel to the plane - included in plane
		expected = p.findIntersections(new Ray(new Point3D(0, 1, 0), new Vector(0, -1, 0)));
		assertNull("Wrong number of points", expected);

		// BVA02: Ray is parallel to the plane - not included in plane
		expected = p.findIntersections(new Ray(new Point3D(0, 0, 2), new Vector(0, 0, 1)));
		assertNull("Wrong number of points", expected);

		// BVA03: Ray is orthogonal to the plane - before it
		expected = p.findIntersections(new Ray(new Point3D(-1, -1, -1), new Vector(0, 0, 1)));
		assertEquals("Wrong number of points", 1, expected.size());
		assertEquals("Wrong points expected", List.of(new Point3D(-1, -1, 0)), expected);

		// BVA04: Ray is orthogonal to the plane - after it
		expected = p.findIntersections(new Ray(new Point3D(1, 1, 1), new Vector(0, 0, 1)));
		assertNull("Wrong number of points", expected);

		// BVA05: Ray is orthogonal to the plane - starts in it
		expected = p.findIntersections(new Ray(new Point3D(0, 0, 0), new Vector(0, 0, 1)));
		assertNull("Wrong number of points", expected);

		// BVA06: Ray starts at the plane, if it isn't included in the other BVAs
		expected = p.findIntersections(new Ray(new Point3D(0, 0, 0), new Vector(1, -1, 0)));
		assertNull("Wrong number of points", expected);

		// BVA07: Ray begins at the point of the plane (not its' direction)
		expected = p.findIntersections(new Ray(new Point3D(0, 0, 0), new Vector(1, -1, 0)));
		assertNull("Wrong number of points", expected);
	}

}
