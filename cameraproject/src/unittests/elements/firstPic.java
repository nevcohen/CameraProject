package unittests.elements;

import org.junit.Test;

import elements.AmbientLight;
import elements.Camera;
import elements.DirectionalLight;
import elements.PointLight;
import elements.SpotLight;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import renderer.Render;
import scene.Scene;

public class firstPic {

	@Test
	public void test() {
		Camera camera = new Camera(new Point3D(250, -50, 125), new Vector(-10, 2.2, -5.9),
				new Vector(-10, 1.5, 103.3 / 5.9)) //
						.setViewPlaneSize(1000, 1000).setViewPlaneDistance(1000);

		Scene scene = new Scene("Test scene");
		scene.setAmbientLight(new AmbientLight(new Color(64, 64, 64), 0.15));

		scene.geometries.add( //
				// Floor and mirror
				new Polygon(new Point3D(0, 100, 0), new Point3D(0, -100, 0), new Point3D(0, -100, 100),
						new Point3D(0, 100, 100)).setEmission(new Color(0, 102, 102))
								.setMaterial(new Material().setKd(0.2).setKs(0.5).setKr(0.7).setShininess(60)), //
				new Polygon(new Point3D(0, 100, 0), new Point3D(150, 100, 0), new Point3D(150, -100, 0),
						new Point3D(0, -100, 0)).setEmission(new Color(0, 51, 51))
								.setMaterial(new Material().setKd(0.3).setKs(0.5).setKr(0.1).setShininess(60)), //
				
				//Exterior walls of the box
				new Polygon(new Point3D(50, -25, 0), new Point3D(50, 25, 0), new Point3D(50, 25, 25),
						new Point3D(50, -25, 25)).setEmission(new Color(0, 0, 0))
								.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
				new Polygon(new Point3D(50, -25, 0), new Point3D(100, -25, 0), new Point3D(100, -25, 25),
						new Point3D(50, -25, 25)).setEmission(new Color(0, 0, 0))
								.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
				new Polygon(new Point3D(100, -25, 0), new Point3D(100, 25, 0), new Point3D(100, 25, 25),
						new Point3D(100, -25, 25)).setEmission(new Color(0, 0, 0))
								.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
				new Polygon(new Point3D(50, 25, 0), new Point3D(100, 25, 0), new Point3D(100, 25, 25),
						new Point3D(50, 25, 25)).setEmission(new Color(0, 0, 0))
								.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
				
				//The top walls of the box
				new Polygon(new Point3D(55, -25, 25), new Point3D(50, -25, 25), new Point3D(50, 25, 25),
						new Point3D(55, 25, 25)).setEmission(new Color(32, 32, 32))
								.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
				new Polygon(new Point3D(55, -25, 25), new Point3D(55, -20, 25), new Point3D(95, -20, 25),
						new Point3D(95, -25, 25)).setEmission(new Color(32, 32, 32))
								.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
				new Polygon(new Point3D(100, -25, 25), new Point3D(95, -25, 25), new Point3D(95, 25, 25),
						new Point3D(100, 25, 25)).setEmission(new Color(32, 32, 32))
								.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
				new Polygon(new Point3D(55, 20, 25), new Point3D(55, 25, 25), new Point3D(95, 25, 25),
						new Point3D(95, 20, 25)).setEmission(new Color(32, 32, 32))
								.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //

				//Inner walls of the box
				new Polygon(new Point3D(55, -25, 25), new Point3D(55, -25, 20), new Point3D(55, 25, 20),
						new Point3D(55, 25, 25)).setEmission(new Color(0, 0, 0))
								.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
				new Polygon(new Point3D(55, -20, 20), new Point3D(55, -20, 25), new Point3D(95, -20, 25),
						new Point3D(95, -20, 20)).setEmission(new Color(0, 0, 0))
								.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
				new Polygon(new Point3D(95, -25, 25), new Point3D(95, -25, 20), new Point3D(95, 25, 20),
						new Point3D(95, 25, 25)).setEmission(new Color(0, 0, 0))
								.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
				new Polygon(new Point3D(55, 20, 20), new Point3D(55, 20, 25), new Point3D(95, 20, 25),
						new Point3D(95, 20, 20)).setEmission(new Color(0, 0, 0))
								.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //

				//The surface on which the diamond rests
				new Polygon(new Point3D(95, -25, 20), new Point3D(55, -25, 20), new Point3D(55, 25, 20),
						new Point3D(95, 25, 20)).setEmission(new Color(64, 32, 32)), //

				//The houses of the lamps
				new Sphere(new Point3D(15, -85, 85), 15).setEmission(new Color(255, 153, 51))
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(35).setKt(0.4)), //
				new Sphere(new Point3D(15, 85, 85), 15).setEmission(new Color(255, 153, 51))
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(35).setKt(0.4)), //

				//The left stick on which the lamp is placed
				new Polygon(new Point3D(25, -83, 70), new Point3D(25, -87, 70), new Point3D(0, -87, 70),
						new Point3D(0, -83, 70)).setEmission(new Color(0, 0, 0))
								.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
				new Polygon(new Point3D(25, -83, 68), new Point3D(25, -87, 68), new Point3D(0, -87, 68),
						new Point3D(0, -83, 68)).setEmission(new Color(0, 0, 0))
								.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
				new Polygon(new Point3D(25, -87, 70), new Point3D(25, -87, 68), new Point3D(0, -87, 68),
						new Point3D(0, -87, 70)).setEmission(new Color(0, 0, 0))
								.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
				new Polygon(new Point3D(25, -83, 70), new Point3D(25, -83, 68), new Point3D(0, -83, 68),
						new Point3D(0, -83, 70)).setEmission(new Color(0, 0, 0))
								.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
				new Polygon(new Point3D(25, -83, 70), new Point3D(25, -87, 70), new Point3D(25, -87, 68),
						new Point3D(25, -83, 68)).setEmission(new Color(0, 0, 0)), //

				//The right stick on which the lamp is placed
				new Polygon(new Point3D(25, 83, 70), new Point3D(25, 87, 70), new Point3D(0, 87, 70),
						new Point3D(0, 83, 70)).setEmission(new Color(0, 0, 0))
								.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
				new Polygon(new Point3D(25, 83, 68), new Point3D(25, 87, 68), new Point3D(0, 87, 68),
						new Point3D(0, 83, 68)).setEmission(new Color(0, 0, 0))
								.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
				new Polygon(new Point3D(25, 87, 70), new Point3D(25, 87, 68), new Point3D(0, 87, 68),
						new Point3D(0, 87, 70)).setEmission(new Color(0, 0, 0))
								.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
				new Polygon(new Point3D(25, 83, 70), new Point3D(25, 83, 68), new Point3D(0, 83, 68),
						new Point3D(0, 83, 70)).setEmission(new Color(0, 0, 0))
								.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
				new Polygon(new Point3D(25, 83, 70), new Point3D(25, 87, 70), new Point3D(25, 87, 68),
						new Point3D(25, 83, 68)).setEmission(new Color(0, 0, 0)), //

				//The lower sides of the diamond (triangles)
				new Triangle(new Point3D(75, 0, 15), new Point3D(66.34, -5, 30), new Point3D(66.34, 5, 30))
						.setEmission(new Color(0, 76, 153))
						.setMaterial(new Material().setKd(0.5).setKs(0.7).setShininess(100).setKt(0.2)), //
				new Triangle(new Point3D(75, 0, 15), new Point3D(83.66, -5, 30), new Point3D(83.66, 5, 30))
						.setEmission(new Color(0, 76, 153))
						.setMaterial(new Material().setKd(0.5).setKs(0.7).setShininess(100).setKt(0.2)), //
				new Triangle(new Point3D(75, 0, 15), new Point3D(66.34, -5, 30), new Point3D(75, -10, 30))
						.setEmission(new Color(0, 76, 153))
						.setMaterial(new Material().setKd(0.5).setKs(0.7).setShininess(100).setKt(0.2)), //
				new Triangle(new Point3D(75, 0, 15), new Point3D(83.66, -5, 30), new Point3D(75, -10, 30))
						.setEmission(new Color(0, 76, 153))
						.setMaterial(new Material().setKd(0.5).setKs(0.7).setShininess(100).setKt(0.2)), //
				new Triangle(new Point3D(75, 0, 15), new Point3D(75, 10, 30), new Point3D(66.34, 5, 30))
						.setEmission(new Color(0, 76, 153))
						.setMaterial(new Material().setKd(0.5).setKs(0.7).setShininess(100).setKt(0.2)), //
				new Triangle(new Point3D(75, 0, 15), new Point3D(75, 10, 30), new Point3D(83.66, 5, 30))
						.setEmission(new Color(0, 76, 153))
						.setMaterial(new Material().setKd(0.5).setKs(0.7).setShininess(100).setKt(0.2)), //

				//Top walls of the diamond (trapezoid)
				new Polygon(new Point3D(71.54, -2, 33), new Point3D(66.34, -5, 30), new Point3D(66.34, 5, 30),
						new Point3D(71.54, 2, 33)).setEmission(new Color(0, 102, 214))
								.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setKt(0.3)), //
				new Polygon(new Point3D(78.46, -2, 33), new Point3D(83.66, -5, 30), new Point3D(83.66, 5, 30),
						new Point3D(78.46, 2, 33)).setEmission(new Color(0, 102, 214))
								.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setKt(0.3)), //
				new Polygon(new Point3D(71.536, -2, 33), new Point3D(66.34, -5, 30), new Point3D(75, -10, 30),
						new Point3D(75, -4, 33)).setEmission(new Color(0, 102, 214))
								.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setKt(0.3)), //
				new Polygon(new Point3D(78.464, -2, 33), new Point3D(83.66, -5, 30), new Point3D(75, -10, 30),
						new Point3D(75, -4, 33)).setEmission(new Color(0, 102, 214))
								.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setKt(0.3)), //
				new Polygon(new Point3D(75, 4, 33), new Point3D(75, 10, 30), new Point3D(66.34, 5, 30),
						new Point3D(71.536, 2, 33)).setEmission(new Color(0, 102, 214))
								.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setKt(0.3)), //
				new Polygon(new Point3D(75, 4, 33), new Point3D(75, 10, 30), new Point3D(83.66, 5, 30),
						new Point3D(78.464, 2, 33)).setEmission(new Color(0, 102, 214))
								.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setKt(0.3)), //

				//The top of the diamond (hexagon)
				new Polygon(new Point3D(71.54, 2, 33), new Point3D(75, 4, 33), new Point3D(78.46, 2, 33),
						new Point3D(78.46, -2, 33), new Point3D(75, -4, 33), new Point3D(71.54, -2, 33))
								.setEmission(new Color(0, 76, 153))
								.setMaterial(new Material().setKd(0.5).setKs(0.7).setShininess(70).setKt(0.2)));

		scene.lights.add(new DirectionalLight(new Color(250, 100, 100), new Vector(-1, -1, -1)));
		scene.lights.add(new PointLight(new Color(500, 250, 250), new Point3D(15, 85, 85)) //
				.setKl(4E-5).setKq(2E-7));
		scene.lights.add(new PointLight(new Color(500, 250, 250), new Point3D(15, -85, 85)) //
				.setKl(4E-5).setKq(2E-7));
		scene.lights
				.add(new SpotLight(new Color(250, 100, 100), new Point3D(150, 0, 25), new Vector(-20, 0, 1)).setExp(3) //
						.setKl(4E-5).setKq(2E-7));
		scene.lights.add(new SpotLight(new Color(250, 100, 100), new Point3D(150, 0, 75), new Vector(-10, 0, -7.5)) //
				.setKl(4E-5).setKq(2E-7));

		ImageWriter imageWriter = new ImageWriter("firstPic", 1000, 1000);
		Render render = new Render() //
				.setImageWriter(imageWriter) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));

		render.renderImage();
		render.writeToImage();
	}

}
