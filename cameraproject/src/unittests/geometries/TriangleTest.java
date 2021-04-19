package unittests.geometries;

import static org.junit.Assert.*;

import java.util.List;

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
	
	@Test
	public void findIntersections(Ray ray) {

        Triangle t = new Triangle(new Point3D(0, 0, 0), new Point3D(2, 1, 0), new Point3D(0, 2, 0));
        List<Point3D> expected;
        
		//EP01:Ray intersects with triangle
		expected = t.findIntersections(new Ray(new Point3D(1, 1, 0), new Vector(0, -1, 0)));
		assertEquals("Wrong number of points", 1, expected.size());
		
		//EP02: Ray does not intersect with the triangle
		expected = t.findIntersections(new Ray(new Point3D(0, 0, 1), new Vector(1, 0, 0)));
		assertEquals("Wrong number of points", 0, expected.size());
		
        // BVA01: Ray is parallel to the plane - included in triangle
		expected = t.findIntersections(new Ray(new Point3D(0, 1, 0), new Vector(0, -1, 0)));
		assertEquals("Wrong number of points", 1, expected.size());
		
        // BVA02: Ray is parallel to the plane - not included in triangle
		expected = t.findIntersections(new Ray(new Point3D(0, 0, 2), new Vector(0, 0, 1)));
		assertEquals("Wrong number of points", 0, expected.size());
		
        // BVA03: Ray is orthogonal to the triangle - before it
		expected = t.findIntersections(new Ray(new Point3D(-1, -1, -1), new Vector(0, 0, 1)));
		assertEquals("Wrong number of points", 1, expected.size());
		
        // BVA04: Ray is orthogonal to the triangle - after it
		expected = t.findIntersections(new Ray(new Point3D(1, 1, 1), new Vector(0, 0, 1)));
		assertEquals("Wrong number of points", 0, expected.size());
		
        // BVA05: Ray is orthogonal to the triangle - starts in it
		expected = t.findIntersections(new Ray(new Point3D(0, 0, 0), new Vector(0, 0, 1)));
		assertEquals("Wrong number of points", 1, expected.size());
		
        // BVA06: Ray starts at the triangle, if it isn't included in the other BVAs
		expected = t.findIntersections(new Ray(new Point3D(0, 0, 0), new Vector(1, -1, 0)));
		assertEquals("Wrong number of points", 1, expected.size());
		
        // BVA07: Ray begins at the point of the triangle (not its' direction)
		expected = t.findIntersections(new Ray(new Point3D(0, 0, 0), new Vector(1, -1, 0)));
		assertEquals("Wrong number of points", 1, expected.size());
		
        // BVA08: Ray is inside the plane if the triangle continued, but isnt
		expected = t.findIntersections(new Ray(new Point3D(-1, -1, 3), new Vector(0, 0, -1)));
		assertEquals("Wrong number of points", 0, expected.size());
	}


}
