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
	 * {@link geometries.Geometries#findGeoIntersections(primitives.Ray)}.
	 */
	@Test
	public void testFindGeoIntersections() {
		Ray ray = new Ray(new Point3D(0.5, 0, 0), new Vector(new Point3D(1, 0, 0)));

		// ============ Equivalence Partitions Tests ==============

		// TC01: Some (but not all) geometric shapes intersect by the Ray (1 with the
		// plane, 2 with the sphere, 0 with the triangle)
		Plane plane = new Plane(new Point3D(1, 0, 0), new Point3D(1, 1, 0), new Point3D(1, 1, 1));
		Sphere sphere = new Sphere(new Point3D(4, 1.5, 0), 2d);
		Triangle triangle = new Triangle(new Point3D(6, -3, -1), new Point3D(8, -1, 2), new Point3D(11, -4, 2));

		Geometries allGeometries = new Geometries(plane, sphere, triangle);

		assertEquals("findGeoIntersections(Ray) Some of the geometric shapes are intersected by the Ray",
				allGeometries.findGeoIntersections(ray).size(), 3);

		// =============== Boundary Values Tests ==================

		// TC11: Empty geometric shapes collection
		Geometries allGeometries2 = new Geometries();

		assertNull("findGeoIntersections(Ray) Empty geometric shapes collection",
				allGeometries2.findGeoIntersections(ray));

		// TC12: All geometric shapes are not intersected by the Ray
		Plane plane1 = new Plane(new Point3D(-1, 0, 0), new Point3D(-1, 1, 0), new Point3D(-1, 1, 1));
		Sphere sphere1 = new Sphere(new Point3D(4, 3, 0), 2d);
		Triangle triangle1 = new Triangle(new Point3D(6, -3, -1), new Point3D(8, -1, 2), new Point3D(11, -4, 2));

		Geometries allGeometries3 = new Geometries(plane1, sphere1, triangle1);

		assertNull("findGeoIntersections(Ray) All geometric shapes are not intersected by the Ray",
				allGeometries3.findGeoIntersections(ray));

		// TC13: Only one geometric shape is intersected by the Ray (1 with the
		// triangle)
		Plane plane2 = new Plane(new Point3D(-1, 0, 0), new Point3D(-1, 1, 0), new Point3D(-1, 1, 1));
		Sphere sphere2 = new Sphere(new Point3D(4, 3, 0), 2d);
		Triangle triangle2 = new Triangle(new Point3D(6, -1, -1), new Point3D(8, 2, 1), new Point3D(11, -2, 2));

		Geometries allGeometries4 = new Geometries(plane2, sphere2, triangle2);

		assertEquals("findGeoIntersections(Ray) Only one geometric shape is intersected by the Ray",
				allGeometries4.findGeoIntersections(ray).size(), 1);

		// TC14: All geometric shapes are intersected by the Ray
		Plane plane3 = new Plane(new Point3D(1, 0, 0), new Point3D(1, 1, 0), new Point3D(1, 1, 1));
		Sphere sphere3 = new Sphere(new Point3D(4, 1.5, 0), 2d);
		Triangle triangle3 = new Triangle(new Point3D(6, -1, -1), new Point3D(8, 2, 1), new Point3D(11, -2, 2));

		Geometries allGeometries5 = new Geometries(plane3, sphere3, triangle3);

		assertEquals("findGeoIntersections(Ray) All geometric shapes are intersected by the Ray",
				allGeometries5.findGeoIntersections(ray).size(), 4);
	}

	/**
	 * Test method for
	 * {@link geometries.Geometries#findGeoIntersections(primitives.Ray, double)}.
	 */
	@Test
	public void testFindGeoIntersectionsMaxDistance() {
		Ray ray = new Ray(new Point3D(0.5, 0, 0), new Vector(new Point3D(1, 0, 0)));

		// ============ Equivalence Partitions Tests ==============

		// TC01: Some (but not all) geometric shapes intersect by the Ray (1 with the
		// plane, 2 with the sphere, 0 with the triangle), all within the maximum range
		// of the distance.
		Plane plane = new Plane(new Point3D(1, 0, 0), new Point3D(1, 1, 0), new Point3D(1, 1, 1));
		Sphere sphere = new Sphere(new Point3D(4, 1.5, 0), 2d);
		Triangle triangle = new Triangle(new Point3D(6, -3, -1), new Point3D(8, -1, 2), new Point3D(11, -4, 2));

		Geometries allGeometries = new Geometries(plane, sphere, triangle);

		assertEquals(
				"findGeoIntersections(Ray, double) Some of the geometric shapes are intersected by the Ray, all within the maximum range of the distance",
				allGeometries.findGeoIntersections(ray, 25).size(), 3);

		// =============== Boundary Values Tests ==================

		// TC11: Empty geometric shapes collection
		Geometries allGeometries2 = new Geometries();

		assertNull("findGeoIntersections(Ray, double) Empty geometric shapes collection",
				allGeometries2.findGeoIntersections(ray, 1000));

		// TC12: All geometric shapes are not intersected by the Ray
		Plane plane1 = new Plane(new Point3D(1, 0, 0), new Point3D(1, 1, 0), new Point3D(1, 1, 1));
		Sphere sphere1 = new Sphere(new Point3D(4, 1.5, 0), 2d);
		Triangle triangle1 = new Triangle(new Point3D(6, -3, -1), new Point3D(8, -1, 2), new Point3D(11, -4, 2));

		Geometries allGeometries3 = new Geometries(plane1, sphere1, triangle1);

		assertNull(
				"findGeoIntersections(Ray, double) All geometric shapes are out of the maximum range of the distance",
				allGeometries3.findGeoIntersections(ray, 0.3));

		// TC13: Only one geometric shape is intersected by the Ray
		Plane plane2 = new Plane(new Point3D(1, 0, 0), new Point3D(1, 1, 0), new Point3D(1, 1, 1));
		Sphere sphere2 = new Sphere(new Point3D(4, 1.5, 0), 2d);
		Triangle triangle2 = new Triangle(new Point3D(6, -1, -1), new Point3D(8, 2, 1), new Point3D(11, -2, 2));

		Geometries allGeometries4 = new Geometries(plane2, sphere2, triangle2);

		assertEquals("findGeoIntersections(Ray, double) Only one geometric shape is intersected by the Ray",
				allGeometries4.findGeoIntersections(ray, 1.0).size(), 1);

		// TC14: All geometric shapes are within the maximum range of the distance
		Plane plane3 = new Plane(new Point3D(1, 0, 0), new Point3D(1, 1, 0), new Point3D(1, 1, 1));
		Sphere sphere3 = new Sphere(new Point3D(4, 1.5, 0), 2d);
		Triangle triangle3 = new Triangle(new Point3D(6, -1, -1), new Point3D(8, 2, 1), new Point3D(11, -2, 2));

		Geometries allGeometries5 = new Geometries(plane3, sphere3, triangle3);

		assertEquals(
				"findGeoIntersections(Ray, double) All geometric shapes are intersected by the Ray, all within the maximum range of the distance",
				allGeometries5.findGeoIntersections(ray, 25).size(), 4);
	}
}
