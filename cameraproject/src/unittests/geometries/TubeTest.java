package unittests.geometries;

import static org.junit.Assert.*;

import org.junit.Test;

import geometries.Tube;
import primitives.*;

/**
 * Testing Tubes
 */
public class TubeTest {

	/**
	 * Test method for {@link geometries.Tube#getNormal(primitives.Point3D)}.
	 */
	@Test
	public void testGetNormal() {
		Tube tube = new Tube(new Ray(new Point3D(0, 0, 2), new Vector(new Point3D(0, 0, 1))), 2);
		// ============ Equivalence Partitions Tests ==============

		// TC01: There is a simple single test here
		assertEquals("", tube.getNormal(new Point3D(0, 2, 0)), new Vector(0, 1, 0));

		// =============== Boundary Values Tests ==================

		// TC11: A point that is on the circle whose starting point is its center
		assertEquals("", tube.getNormal(new Point3D(0, 2, 2)), new Vector(0, 1, 0));
	}

}
