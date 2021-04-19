package unittests.geometries;

import static org.junit.Assert.*;

import java.util.ArrayList;
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
		assertEquals("Mistake found with GetNormal", p.getNormal(new Point3D(7, -2, 1)),
				new Vector(1, 2, 3).normalize());
	}
	
	@Test
	public void findIntersections(Ray ray) {
		Plane p = new Plane(new Point3D(-1, 0, -1), new Vector(1, -3, -5));
		//EP01:Ray intersects with plane
		List<Point3D> expected 
		//EP02: Ray does not intersect with the plane
        // BVA01: There is a simple single test here
        // BVA02: There is a simple single test here
        // BVA03: There is a simple single test here
        // BVA04: There is a simple single test here
        // BVA05: There is a simple single test here
        // BVA06: There is a simple single test here
        // BVA07: There is a simple single test here
		
		
        // TC01: There is a simple single test here
		assertEquals("Mistake found with findIntersections", p.findIntersections(new Ray(new Point3D(0,0,0),new Vector(3,1,2))).size(),
				1);
	}

}
