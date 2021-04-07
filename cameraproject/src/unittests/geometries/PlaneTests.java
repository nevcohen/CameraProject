package unittests.geometries;

import static org.junit.Assert.*;

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

}
