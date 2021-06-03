package unittests.elements;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import elements.Camera;
import primitives.*;

/**
 * Testing Camera Class
 * 
 * @author Dan
 *
 */
public class CameraTests {

	/**
	 * Test method for
	 * {@link elements.Camera#constructRayThroughPixel(int, int, int, int)}.
	 */
	@Test
	public void testConstructRayThroughPixel() {
		Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0)).setViewPlaneDistance(10);

		// ============ Equivalence Partitions Tests ==============
		// TC01: 3X3 Corner (0,0)
		assertEquals("Bad ray", new Ray(Point3D.ZERO, new Vector(-2, -2, 10)),
				camera.setViewPlaneSize(6, 6).constructRayThroughPixel(3, 3, 0, 0));

		// TC02: 4X4 Corner (0,0)
		assertEquals("Bad ray", new Ray(Point3D.ZERO, new Vector(-3, -3, 10)),
				camera.setViewPlaneSize(8, 8).constructRayThroughPixel(4, 4, 0, 0));

		// TC03: 4X4 Side (0,1)
		assertEquals("Bad ray", new Ray(Point3D.ZERO, new Vector(-1, -3, 10)),
				camera.setViewPlaneSize(8, 8).constructRayThroughPixel(4, 4, 1, 0));

		// TC04: 4X4 Inside (1,1)
		assertEquals("Bad ray", new Ray(Point3D.ZERO, new Vector(-1, -1, 10)),
				camera.setViewPlaneSize(8, 8).constructRayThroughPixel(4, 4, 1, 1));

		// =============== Boundary Values Tests ==================
		// TC11: 3X3 Center (1,1)
		assertEquals("Bad ray", new Ray(Point3D.ZERO, new Vector(0, 0, 10)),
				camera.setViewPlaneSize(6, 6).constructRayThroughPixel(3, 3, 1, 1));

		// TC12: 3X3 Center of Upper Side (0,1)
		assertEquals("Bad ray", new Ray(Point3D.ZERO, new Vector(0, -2, 10)),
				camera.setViewPlaneSize(6, 6).constructRayThroughPixel(3, 3, 1, 0));

		// TC13: 3X3 Center of Left Side (1,0)
		assertEquals("Bad ray", new Ray(Point3D.ZERO, new Vector(-2, 0, 10)),
				camera.setViewPlaneSize(6, 6).constructRayThroughPixel(3, 3, 0, 1));

	}

	/**
	 * Test method for
	 * {@link elements.Camera#creatGrid(Point3D,double,int,boolean)}.
	 */
	@Test
	public void testCreatGrid() {
		Camera camera = new Camera(new Point3D(0, 0, 0), new Vector(0, 0, -1), new Vector(-1, 0, 0));
		List<Point3D> allPoints = camera.creatGrid(new Point3D(0, 0, 0), 4, 4, true);
		List<Point3D> expectedPoints = List.of(new Point3D(1.5, -0.5, 0), new Point3D(1.5, 0.5, 0),
				new Point3D(0.5, -1.5, 0), new Point3D(0.5, -0.5, 0), new Point3D(0.5, 0.5, 0),
				new Point3D(0.5, 1.5, 0), new Point3D(-0.5, -1.5, 0), new Point3D(-0.5, -0.5, 0),
				new Point3D(-0.5, 0.5, 0), new Point3D(-0.5, 1.5, 0), new Point3D(-1.5, -0.5, 0),
				new Point3D(-1.5, 0.5, 0));
		int size = expectedPoints.size();

		assertEquals("Wrong number of pounts", size, allPoints.size());

		for (int i = 0; i < size; i++) {
			assertEquals("Problem with the points", expectedPoints.get(i), allPoints.get(i));
		}
	}

	/**
	 * Test method for {@link elements.Camera#focusRays(Point3D)}.
	 */
	@Test
	public void testFocusRays() {
		Camera camera = new Camera(new Point3D(0, 0, 1100), new Vector(0, 0, -1), new Vector(-1, 0, 0)) //
				.setViewPlaneSize(1, 1).setViewPlaneDistance(1000).setAperture(1.5, 3).setFocalPlaneDistance(1100);

		List<Ray> rays = camera.focusRays(new Point3D(0, 0, 100));
		List<Ray> expectedRays = List.of(new Ray(new Point3D(1, -1, 1100), new Vector(new Point3D(-1, 1, -1100))),
				new Ray(new Point3D(1, 0, 1100), new Vector(new Point3D(-1, 0, -1100))),
				new Ray(new Point3D(1, 1, 1100), new Vector(new Point3D(-1, -1, -1100))),
				new Ray(new Point3D(0, -1, 1100), new Vector(new Point3D(0, 1, -1100))),
				new Ray(new Point3D(0, 1, 1100), new Vector(new Point3D(0, -1, -1100))),
				new Ray(new Point3D(-1, -1, 1100), new Vector(new Point3D(1, 1, -1100))),
				new Ray(new Point3D(-1, 0, 1100), new Vector(new Point3D(1, 0, -1100))),
				new Ray(new Point3D(-1, 1, 1100), new Vector(new Point3D(1, -1, -1100))));

		int size = expectedRays.size();
		assertEquals("Wrong number of rays", size, rays.size());
		for (int i = 0; i < size; i++)
			assertEquals("Problem with the rays", expectedRays.get(i), rays.get(i));
	}

	/**
	 * Test method for {@link elements.Camera#pixelRays(Point3D)}.
	 */
	@Test
	public void testPixelRays() {
		Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(-1, 0, 0)) //
				.setViewPlaneSize(1, 1).setViewPlaneDistance(1000).setPixelGridSize(2);

		List<Ray> rays = camera.pixelRays(new Point3D(0, 0, 0));
		List<Ray> expectedRays = List.of(new Ray(new Point3D(0, 0, 1000), new Vector(new Point3D(0.25, -0.25, -1000))),
				new Ray(new Point3D(0, 0, 1000), new Vector(new Point3D(0.25, 0.25, -1000))),
				new Ray(new Point3D(0, 0, 1000), new Vector(new Point3D(-0.25, -0.25, -1000))),
				new Ray(new Point3D(0, 0, 1000), new Vector(new Point3D(-0.25, 0.25, -1000))));

		assertEquals("Wrong number of rays", 4, rays.size());
		assertEquals("Problem with the first point", expectedRays.get(0), rays.get(0));
		assertEquals("Problem with the second point", expectedRays.get(1), rays.get(1));
		assertEquals("Problem with the third point", expectedRays.get(2), rays.get(2));
		assertEquals("Problem with the fourth point", expectedRays.get(3), rays.get(3));
	}

}
