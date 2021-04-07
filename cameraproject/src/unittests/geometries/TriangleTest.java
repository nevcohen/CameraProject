package unittests.geometries;

import static org.junit.Assert.*;

import org.junit.Test;

import geometries.*;
import primitives.*;

/**
 * Testing Triangles
 */
public class TriangleTest {

	/**
	 * Test method for {@link geometries.Polygon#getNormal(primitives.Point3D)}.
	 */
	@Test
	public void testGetNormal() {
		Triangle newTriangle = new Triangle(new Point3D(1, 1, 1), new Point3D(3, 0, 1), new Point3D(6, 0, 0));
		double x = Math.sqrt(14);
		// ============ Equivalence Partitions Tests ==============

		// TC01: There is a simple single test here
		assertEquals("getNormal() Bad normal to Triangle", new Vector(new Point3D(1 / x, 2 / x, 3 / x)),
				newTriangle.getNormal(new Point3D(4, 1d / 4, 1d / 2)));
	}

}
