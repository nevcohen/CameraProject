package unittests.elements;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import elements.Camera;
import elements.DirectionalLight;
import geometries.Sphere;
import geometries.Triangle;
import primitives.*;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import renderer.Render;
import scene.Scene;

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
	
	@Test
	public void testFocusRays() {
		Camera camera = new Camera(new Point3D(0, 0, 1100), new Vector(0, 0, -1), new Vector(-1, 0, 0)) //
				.setViewPlaneSize(1, 1).setViewPlaneDistance(1000).setApertureRadius(1).setFocalPlaneDistance(100).setApertureScale(2);
		
		List<Ray> rays = camera.focusRays(new Point3D(0, 0, 0));
		List<Ray> expectedRays = List.of(
				new Ray(new Point3D(2, 0, 1100), new Vector(new Point3D(-2, 0, -1100))),
				new Ray(new Point3D(0, -2, 1100), new Vector(new Point3D(0, 2, -1100))),
				new Ray(new Point3D(0, 2, 1100), new Vector(new Point3D(0, -2, -1100))),
				new Ray(new Point3D(-2, 0, 1100), new Vector(new Point3D(2, 0, -1100))));
		
		assertEquals("-----------", 4,rays.size());
		assertEquals("-----------", expectedRays.get(0),rays.get(0));
		assertEquals("-----------", expectedRays.get(1),rays.get(1));
		assertEquals("-----------", expectedRays.get(2),rays.get(2));
		assertEquals("-----------", expectedRays.get(3),rays.get(3));
	}

	@Test
	public void testPixelRays() {
		Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(-1, 0, 0)) //
				.setViewPlaneSize(1, 1).setViewPlaneDistance(1000).setPixelGridLength(2);
		
		List<Ray> rays = camera.pixelRays(new Point3D(0, 0, 0));
		List<Ray> expectedRays = List.of(
				new Ray(new Point3D(0, 0, 1000), new Vector(new Point3D(0.25, -0.25, -1000))),
				new Ray(new Point3D(0, 0, 1000), new Vector(new Point3D(0.25, 0.25, -1000))),
				new Ray(new Point3D(0, 0, 1000), new Vector(new Point3D(-0.25, -0.25, -1000))),
				new Ray(new Point3D(0, 0, 1000), new Vector(new Point3D(-0.25, 0.25, -1000))));
		
		assertEquals("-----------", 4,rays.size());
		assertEquals("-----------", expectedRays.get(0),rays.get(0));
		assertEquals("-----------", expectedRays.get(1),rays.get(1));
		assertEquals("-----------", expectedRays.get(2),rays.get(2));
		assertEquals("-----------", expectedRays.get(3),rays.get(3));
	}
	
	/**
	 * -----------------
	 */
	@Test
	public void testConstructRaysThroughPixel() {
		Scene scene = new Scene("Test scene");
		Camera camera = new Camera(new Point3D(1100, 0, 0), new Vector(-1, 0, 0), new Vector(0, 0, 1)) //
				.setViewPlaneSize(300, 300).setViewPlaneDistance(1000).setApertureRadius(6).setFocalPlaneDistance(600)
				.setApertureScale(8);//.setPixelGridLength(4);

		scene.geometries.add(new Sphere(new Point3D(-500, 0, 0), 60) //
				.setEmission(new Color(java.awt.Color.RED)) //
				.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),
				new Sphere(new Point3D(-1000, 110, 0), 60) //
						.setEmission(new Color(java.awt.Color.GREEN)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),
				new Sphere(new Point3D(-1000, -110, 0), 60) //
						.setEmission(new Color(java.awt.Color.GREEN)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),
				new Sphere(new Point3D(0, 0, 110), 60) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),
				new Sphere(new Point3D(0, 0, -110), 60) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),
				new Triangle(new Point3D(-1250, -100, 70), new Point3D(-1250, -160, 70), new Point3D(-1250, -130, 130))
						.setEmission(new Color(0, 76, 153))
						.setMaterial(new Material().setKd(0.5).setKs(0.7).setShininess(100).setKt(0.2)),
				new Triangle(new Point3D(-750, 100, 70), new Point3D(-750, 160, 70), new Point3D(-750, 130, 130))
						.setEmission(new Color(0, 76, 153))
						.setMaterial(new Material().setKd(0.5).setKs(0.7).setShininess(100).setKt(0.2)),
				new Triangle(new Point3D(-250, -70, -120), new Point3D(-250, -130, -120), new Point3D(-250, -100, -60))
						.setEmission(new Color(0, 76, 153))
						.setMaterial(new Material().setKd(0.5).setKs(0.7).setShininess(100).setKt(0.2)));

		scene.lights.add(new DirectionalLight(new Color(400, 240, 0), new Vector(-1, 0, 0)));

		Render render = new Render(). //
				setImageWriter(new ImageWriter("focus", 600, 600)) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));
		render.renderImage();
		render.writeToImage();
	}

}
