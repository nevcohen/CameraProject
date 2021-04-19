/**
 * 
 */
package unittests.geometries;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import geometries.*;
import primitives.*;

/**
 * Testing Polygons
 * 
 * @author Dan
 *
 */
public class PolygonTests {

	/**
	 * Test method for
	 * {@link geometries.Polygon#Polygon(primitives.Point3D, primitives.Point3D, primitives.Point3D, primitives.Point3D)}.
	 */
	@Test
	public void testConstructor() {
		// ============ Equivalence Partitions Tests ==============

		// TC01: Correct concave quadrangular with vertices in correct order
		try {
			new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0), new Point3D(-1, 1, 1));
		} catch (IllegalArgumentException e) {
			fail("Failed constructing a correct polygon");
		}

		// TC02: Wrong vertices order
		try {
			new Polygon(new Point3D(0, 0, 1), new Point3D(0, 1, 0), new Point3D(1, 0, 0), new Point3D(-1, 1, 1));
			fail("Constructed a polygon with wrong order of vertices");
		} catch (IllegalArgumentException e) {
		}

		// TC03: Not in the same plane
		try {
			new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0), new Point3D(0, 2, 2));
			fail("Constructed a polygon with vertices that are not in the same plane");
		} catch (IllegalArgumentException e) {
		}

		// TC04: Concave quadrangular
		try {
			new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0), new Point3D(0.5, 0.25, 0.5));
			fail("Constructed a concave polygon");
		} catch (IllegalArgumentException e) {
		}

		// =============== Boundary Values Tests ==================

		// TC10: Vertex on a side of a quadrangular
		try {
			new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0), new Point3D(0, 0.5, 0.5));
			fail("Constructed a polygon with vertix on a side");
		} catch (IllegalArgumentException e) {
		}

		// TC11: Last point = first point
		try {
			new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0), new Point3D(0, 0, 1));
			fail("Constructed a polygon with vertice on a side");
		} catch (IllegalArgumentException e) {
		}

		// TC12: Colocated points
		try {
			new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0), new Point3D(0, 1, 0));
			fail("Constructed a polygon with vertice on a side");
		} catch (IllegalArgumentException e) {
		}

	}

	/**
	 * Test method for {@link geometries.Polygon#getNormal(primitives.Point3D)}.
	 */
	@Test
	public void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: There is a simple single test here
		Polygon pl = new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0),
				new Point3D(-1, 1, 1));
		double sqrt3 = Math.sqrt(1d / 3);
		assertEquals("Bad normal to trinagle", new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point3D(0, 0, 1)));
	}

	/**
	 * Test method for {@link geometries.Polygon#findIntersections(primitives.Ray)}.
	 */
	@Test
	public void testFindIntersections() {

		// ============ Equivalence Partitions Tests ==============
		Polygon square = new Polygon(new Point3D(2, 2, 1), new Point3D(2, -2, 1), new Point3D(-2, -2, 1),
				new Point3D(-2, 2, 1));

		// TC01: Inside square
		Ray ray = new Ray(new Point3D(1, 1, 0), new Vector(0, 0, 1));
		List<Point3D> result = square.findIntersections(ray);
		assertEquals("Ray intersection inside square", result.size(), 1);
		assertEquals("Wrong point value", List.of(new Point3D(1, 1, 1)), result);

		// TC02: Outside against edge
		ray = new Ray(new Point3D(3, -1, 0), new Vector(0, 0, 1));
		assertNull("Ray is outside against edge", square.findIntersections(ray));

		// TC03: Outside against vertex
		ray = new Ray(new Point3D(-3, 3, 0), new Vector(0, 0, 1));
		assertNull("Ray is outside against vertex", square.findIntersections(ray));

		// =============== Boundary Values Tests ==================

		// TC10: Ray begins before the plane, intersection on edge
		ray = new Ray(new Point3D(2, 1, 0), new Vector(0, 0, 1));
		assertNull("Ray is intersection on edge", square.findIntersections(ray));

		// TC11: Ray begins before the plane, intersection on vertex
		ray = new Ray(new Point3D(-2, -2, 0), new Vector(0, 0, 1));
		assertNull("Ray is intersection on vertex", square.findIntersections(ray));

		// TC12: Ray begins before the plane and passes on edge's continuation
		ray = new Ray(new Point3D(2, 3, 0), new Vector(0, 0, 1));
		assertNull("Ray is on edge's continuation", square.findIntersections(ray));
	}

}
