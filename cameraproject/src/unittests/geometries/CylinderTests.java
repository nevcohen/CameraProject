/**
 * 
 */
package unittests.geometries;

import static org.junit.Assert.*;

import org.junit.Test;

import geometries.Cylinder;
import geometries.Tube;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * Testing Cylinder
 */
public class CylinderTests {

	/**
	 * Test method for {@link geometries.Cylinder#getNormal(primitives.Point3D)}.
	 */
	@Test
	public void testGetNormal() {
		Cylinder cylinder = new Cylinder(new Ray(new Point3D(0, 0, 0), new Vector(new Point3D(0, 0, 1))), 2, 5);
		// ============ Equivalence Partitions Tests ==============

		// TC01: The point on the cylinder itself
		assertEquals("getNormal() The point on the cylinder itself returns an abnormal normal",
				cylinder.getNormal(new Point3D(0, 2, 3)), new Vector(0, 1, 0));

		// TC02: The point on the lower base of the cylinder
		assertEquals("getNormal() The point on the lower base of the cylinder returns an abnormal normal",
				cylinder.getNormal(new Point3D(1.5, 1, 0)), new Vector(0, 0, -1));

		// TC03: The point on the upper base of the cylinder
		assertEquals("getNormal() The point on the upper base of the cylinder returns an abnormal normal",
				cylinder.getNormal(new Point3D(-0.5, 1.8, 5)), new Vector(0, 0, 1));

		// =============== Boundary Values Tests ==================

		// TC11: The point on the center of the lower base of the cylinder
		assertEquals("getNormal() The point on the center of the lower base of the cylinder returns an abnormal normal",
				cylinder.getNormal(new Point3D(0, 0, 0)), new Vector(0, 0, -1));

		// TC12: The point on the center of the upper base of the cylinder
		assertEquals("getNormal() The point on the center of the upper base of the cylinder returns an abnormal normal",
				cylinder.getNormal(new Point3D(0, 0, 5)), new Vector(0, 0, 1));

		// TC13: The point is on the border of the lower base
		assertEquals("getNormal() The point is on the border of the lower base returns an abnormal normal",
				cylinder.getNormal(new Point3D(2, -2, 0)), new Vector(0, 0, -1));

		// TC14: The point is on the border of the upper base
		assertEquals("getNormal() The point is on the border of the upper base returns an abnormal normal",
				cylinder.getNormal(new Point3D(-2, -2, 5)), new Vector(0, 0, 1));
	}

}
