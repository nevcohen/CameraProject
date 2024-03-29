package unittests.elements;

import org.junit.Test;

import elements.Camera;
import elements.DirectionalLight;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.RayTracer;
import renderer.RayTracerBasic;
import renderer.Render;
import scene.Scene;

public class SuperSamplingTests {

	/**
	 * Test for Depth of Field
	 */
	@Test
	public void testDepthOfField() {
		Scene scene = new Scene("Test scene");
		Camera camera = new Camera(new Point3D(1100, 0, 0), new Vector(-1, 0, 0), new Vector(0, 0, 1)) //
				.setViewPlaneSize(300, 300).setViewPlaneDistance(1000) //
				.setAperture(15, 10).setFocalPlaneDistance(1600);

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
				setImageWriter(new ImageWriter("DepthOfField2", 600, 600)) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.setDebugPrint() //
				.setAsMultyRays() //
				.setMultithreading(3);
		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Test for Anti Aliasing (OFF)
	 */
	@Test
	public void testAntiAliasing1() {
		Scene scene = new Scene("Test scene");
		Camera camera = new Camera(new Point3D(1100, 0, 0), new Vector(-1, 0, 0), new Vector(0, 0, 1)) //
				.setViewPlaneSize(100, 100).setViewPlaneDistance(1000);

		scene.geometries.add(new Sphere(new Point3D(0, 0, 0), 40) //
				.setEmission(new Color(java.awt.Color.RED)) //
				.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)));

		scene.lights.add(new DirectionalLight(new Color(400, 240, 0), new Vector(-1, 0, 0)));

		Render render = new Render(). //
				setImageWriter(new ImageWriter("AntiAliasingOFF", 600, 600)) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));
		render.renderImage();
		render.writeToImage();
	}

	@Test
	public void tes3() {
		Scene scene = new Scene("Test scene");
		Camera camera = new Camera(new Point3D(1000, 0, 0), new Vector(-1, 0, 0), new Vector(0, 0, 1)) //
				.setViewPlaneSize(20, 20).setViewPlaneDistance(1000);

		//scene.geometries.add(new Sphere(new Point3D(-10, 0, 0), 20) //
		//		.setEmission(new Color(java.awt.Color.RED)) //
		//		.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)));
		scene.geometries.add(new Triangle(new Point3D(-20, -4, 2), new Point3D(0, 0, -2), new Point3D(-20, 4, 2)) //
				.setEmission(new Color(java.awt.Color.RED)) //
				.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30).setKr(0.8)));
		scene.geometries.add(new Triangle(new Point3D(-15, 1, 1), new Point3D(-15, 0, 2), new Point3D(-15, -1, 1)) //
				.setEmission(new Color(java.awt.Color.BLUE)) //
				.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)));
		

		scene.lights.add(new DirectionalLight(new Color(400, 240, 0), new Vector(-1, 1, 0)));

		Render render = new Render(). //
				setImageWriter(new ImageWriter("xxx", 200, 200)) //
				.setCamera(camera) //
				.setRayTracer(new RayTracer(scene, 4));
		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Test for Anti Aliasing (ON)
	 */
	@Test
	public void testAntiAliasing2() {
		Scene scene = new Scene("Test scene");
		Camera camera = new Camera(new Point3D(1100, 0, 0), new Vector(-1, 0, 0), new Vector(0, 0, 1)) //
				.setViewPlaneSize(100, 100).setViewPlaneDistance(1000).setPixelGridSize(8);

		scene.geometries.add(new Sphere(new Point3D(0, 0, 0), 40) //
				.setEmission(new Color(java.awt.Color.RED)) //
				.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)));

		scene.lights.add(new DirectionalLight(new Color(400, 240, 0), new Vector(-1, 0, 0)));

		Render render = new Render(). //
				setImageWriter(new ImageWriter("AntiAliasingON", 600, 600)) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.setDebugPrint() //
				.setAsMultyRays() //
				.setMultithreading(3);
		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Test for Depth of Field with Anti Aliasing
	 */
	@Test
	public void testDepthOfFieldAndAntiAliasing() {
		Scene scene = new Scene("Test scene");
		Camera camera = new Camera(new Point3D(1100, 0, 0), new Vector(-1, 0, 0), new Vector(0, 0, 1)) //
				.setViewPlaneSize(300, 300).setViewPlaneDistance(1000).setPixelGridSize(7) //
				.setAperture(6, 8).setFocalPlaneDistance(1600);

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
				setImageWriter(new ImageWriter("DOFandAA2", 600, 600)) //
				.setCamera(camera) //
				.setRayTracer(new RayTracer(scene, 100)) //
				.setAsMultyRays() //
				.setDebugPrint() //
				.setMultithreading(3);
		render.renderImage();
		render.writeToImage();
	}
}
