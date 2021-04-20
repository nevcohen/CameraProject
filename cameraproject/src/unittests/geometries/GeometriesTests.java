/**
 * 
 */
package unittests.geometries;

import static org.junit.Assert.*;

import org.junit.Test;

import geometries.*;
import primitives.*;

/**
 * Testing Geometries
 */
public class GeometriesTests {

	/**
	 * Test method for
	 * {@link geometries.Geometries#findIntersections(primitives.Ray)}.
	 */
	@Test
	public void testFindIntersections() {
		Ray ray = new Ray(new Point3D(0.5, 0, 0), new Vector(new Point3D(1, 0, 0)));

		// ============ Equivalence Partitions Tests ==============

		// TC01: Some (but not all) geometric shapes intersect by the Ray (1 with the
		// plane, 2 with the sphere, 0 with the triangle)
		Plane plane = new Plane(new Point3D(1, 0, 0), new Point3D(1, 1, 0), new Point3D(1, 1, 1));
		Sphere sphere = new Sphere(new Point3D(4, 1.5, 0), 2d);
		Triangle triangle = new Triangle(new Point3D(6, -3, -1), new Point3D(8, -1, 2), new Point3D(11, -4, 2));

		Geometries allGeometries = new Geometries(plane, sphere, triangle);

		assertEquals("findIntersections() Some of the geometric shapes are intersected by the Ray",
				allGeometries.findIntersections(ray).size(), 3);

		// =============== Boundary Values Tests ==================

		// TC11: Empty geometric shapes collection
		Geometries allGeometries2 = new Geometries();

		assertNull("findIntersections() Empty geometric shapes collection", allGeometries2.findIntersections(ray));

		// TC12: All geometric shapes are not intersected by the Ray
		Plane plane1 = new Plane(new Point3D(-1, 0, 0), new Point3D(-1, 1, 0), new Point3D(-1, 1, 1));
		Sphere sphere1 = new Sphere(new Point3D(4, 3, 0), 2d);
		Triangle triangle1 = new Triangle(new Point3D(6, -3, -1), new Point3D(8, -1, 2), new Point3D(11, -4, 2));

		Geometries allGeometries3 = new Geometries(plane1, sphere1, triangle1);

		assertNull("findIntersections() All geometric shapes are not intersected by the Ray",
				allGeometries3.findIntersections(ray));

		// TC13: Only one geometric shape is intersected by the Ray (1 with the
		// triangle)
		Plane plane2 = new Plane(new Point3D(-1, 0, 0), new Point3D(-1, 1, 0), new Point3D(-1, 1, 1));
		Sphere sphere2 = new Sphere(new Point3D(4, 3, 0), 2d);
		Triangle triangle2 = new Triangle(new Point3D(6, -1, -1), new Point3D(8, 2, 1), new Point3D(11, -2, 2));

		Geometries allGeometries4 = new Geometries(plane2, sphere2, triangle2);

		assertEquals("findIntersections() Only one geometric shape is intersected by the Ray",
				allGeometries4.findIntersections(ray).size(), 1);

		// TC14: All geometric shapes are intersected by the Ray
		Plane plane3 = new Plane(new Point3D(1, 0, 0), new Point3D(1, 1, 0), new Point3D(1, 1, 1));
		Sphere sphere3 = new Sphere(new Point3D(4, 1.5, 0), 2d);
		Triangle triangle3 = new Triangle(new Point3D(6, -1, -1), new Point3D(8, 2, 1), new Point3D(11, -2, 2));

		Geometries allGeometries5 = new Geometries(plane3, sphere3, triangle3);

		assertEquals("findIntersections() All geometric shapes are intersected by the Ray",
				allGeometries5.findIntersections(ray).size(), 4);
	}

}
