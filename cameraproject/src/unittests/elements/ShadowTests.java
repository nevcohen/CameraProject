package unittests.elements;

import org.junit.Test;

import elements.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Testing basic shadows
 * 
 * @author Dan
 */
public class ShadowTests {
	private Scene scene = new Scene("Test scene");
	private Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
			.setViewPlaneSize(200, 200).setViewPlaneDistance(1000);

	/**
	 * Produce a picture of a sphere and triangle with point light and shade
	 */
	@Test
	public void sphereTriangleInitial() {
		scene.geometries.add( //
				new Sphere(new Point3D(0, 0, -200), 60) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)), //
				new Triangle(new Point3D(-70, -40, 0), new Point3D(-40, -70, 0), new Point3D(-68, -68, -4)) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)) //
		);
		scene.lights.add( //
				new SpotLight(new Color(400, 240, 0), new Point3D(-100, -100, 200), new Vector(1, 1, -3)) //
						.setKl(1E-5).setKq(1.5E-7));

		Render render = new Render(). //
				setImageWriter(new ImageWriter("shadowSphereTriangleInitial", 400, 400)) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));
		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a two triangles lighted by a spot light with a Sphere
	 * producing a shading
	 */
	@Test
	public void trianglesSphere() {
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

		scene.geometries.add( //
				new Triangle(new Point3D(-150, -150, -115), new Point3D(150, -150, -135), new Point3D(75, 75, -150)) //
						.setMaterial(new Material().setKs(0.8).setShininess(60)), //
				new Triangle(new Point3D(-150, -150, -115), new Point3D(-70, 70, -140), new Point3D(75, 75, -150)) //
						.setMaterial(new Material().setKs(0.8).setShininess(60)), //
				new Sphere(new Point3D(0, 0, -115), 30) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)) //
		);
		scene.lights.add( //
				new SpotLight(new Color(700, 400, 400), new Point3D(40, 40, 115), new Vector(-1, -1, -4)) //
						.setKl(4E-4).setKq(2E-5));

		Render render = new Render() //
				.setImageWriter(new ImageWriter("shadowTrianglesSphere", 600, 600)) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));
		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a sphere and triangle with point light and shade,
	 * changing triangle position up
	 */
	@Test
	public void sphereTriangleInitialTriangleUp() {
		scene.geometries.add( //
				new Sphere(new Point3D(0, 0, -200), 60) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)), //
				new Triangle(new Point3D(-60, -30, -10), new Point3D(-30, -60, -10), new Point3D(-58, -58, -14)) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)) //
		);
		scene.lights.add( //
				new SpotLight(new Color(400, 240, 0), new Point3D(-100, -100, 200), new Vector(1, 1, -3)) //
						.setKl(1E-5).setKq(1.5E-7));

		Render render = new Render(). //
				setImageWriter(new ImageWriter("shadowSphereTriangleInitialTriangleUp", 400, 400)) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));
		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a sphere and triangle with point light and shade,
	 * changing triangle position forward
	 */
	@Test
	public void sphereTriangleInitialTriangleForward() {
		scene.geometries.add( //
				new Sphere(new Point3D(0, 0, -200), 60) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)), //
				new Triangle(new Point3D(-48, -18, 0), new Point3D(-18, -48, 0), new Point3D(-46, -46, -4)) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)) //
		);
		scene.lights.add( //
				new SpotLight(new Color(400, 240, 0), new Point3D(-100, -100, 200), new Vector(1, 1, -3)) //
						.setKl(1E-5).setKq(1.5E-7));

		Render render = new Render(). //
				setImageWriter(new ImageWriter("shadowSphereTriangleInitialTriangleForward", 400, 400)) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));
		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a sphere and triangle with point light and shade,
	 * changing light position up
	 */
	@Test
	public void sphereTriangleInitialLightUp() {
		scene.geometries.add( //
				new Sphere(new Point3D(0, 0, -200), 60) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)), //
				new Triangle(new Point3D(-70, -40, 0), new Point3D(-40, -70, 0), new Point3D(-68, -68, -4)) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)) //
		);
		scene.lights.add( //
				new SpotLight(new Color(400, 240, 0), new Point3D(-90, -90, 170), new Vector(1, 1, -3)) //
						.setKl(1E-5).setKq(1.5E-7));

		Render render = new Render(). //
				setImageWriter(new ImageWriter("shadowSphereTriangleInitialLightUp", 400, 400)) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));
		render.renderImage();
		render.writeToImage();
	}
	
	/**
	 * Produce a picture of a sphere and triangle with point light and shade,
	 * changing light position forward
	 */
	@Test
	public void sphereTriangleInitialLightForward() {
		scene.geometries.add( //
				new Sphere(new Point3D(0, 0, -200), 60) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)), //
				new Triangle(new Point3D(-70, -40, 0), new Point3D(-40, -70, 0), new Point3D(-68, -68, -4)) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)) //
		);
		scene.lights.add( //
				new SpotLight(new Color(400, 240, 0), new Point3D(-75, -75, 60), new Vector(1, 1, -3)) //
						.setKl(1E-5).setKq(1.5E-7));

		Render render = new Render(). //
				setImageWriter(new ImageWriter("shadowSphereTriangleInitialLightForward", 400, 400)) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));
		render.renderImage();
		render.writeToImage();
	}
}
