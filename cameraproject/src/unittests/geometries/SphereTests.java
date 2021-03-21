package unittests.geometries;

import static org.junit.Assert.*;

import org.junit.Test;

import geometries.Plane;
import geometries.Sphere;
import primitives.Point3D;
import primitives.Vector;

/**
 * Testing Spheres
 */
public class SphereTests {

	/**
	 * Test method for {@link geometries.Sphere#getNormal(primitives.Point3D)}.
	 */
	@Test
	public void testGetNormal() {
		Sphere s = new Sphere(new Point3D(1, 1, 1), 2);

		// ============ Equivalence Partitions Tests ==============
		
		// TC01: There is a simple single test here
		assertEquals("", s.getNormal(new Point3D(1, 1, 3)), new Vector(0, 0, 1));
	}
}
