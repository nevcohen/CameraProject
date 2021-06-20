package unittests.elements;

import java.util.List;

import org.junit.Test;

import elements.AmbientLight;
import elements.Camera;
import elements.DirectionalLight;
import elements.PointLight;
import geometries.Plane;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import renderer.*;
import scene.Scene;

public class NoahsArk {

	@Test
	public void test() {
		Camera camera = new Camera(new Point3D(2500, 500, 600), new Vector(-10, -4, -3),
				new Vector(-10, -4, 116.0 / 3.0)) //
						.setViewPlaneDistance(1000).setViewPlaneSize(800, 800) //
						.setPixelGridSize(9);
						//.setAperture(8, 8).setFocalPlaneDistance(1700);
		Scene scene = new Scene("Test scene").setAmbientLight(new AmbientLight(new Color(java.awt.Color.GRAY), 0.2))
				.setBackground(new Color(255, 255, 224));

		Color colorArk1 = new Color(119, 56, 12);
		Color colorArk2 = new Color(139, 69, 19);
		List<Color> colorArk = List.of(colorArk1, colorArk2);
		Color colorArkInsid = new Color(128, 128, 128);
		Color colorTopArk = new Color(139, 69, 19);
		Color colorRoofArk = new Color(205, 133, 63);
		// Color colorSea = new Color(72, 209, 204);
		Color colorSea = new Color(49, 125, 125);

		Material mat = new Material().setKd(0.2).setKs(0.3).setShininess(30);
		Material matSea = new Material().setKd(0.2).setKs(0.5).setShininess(40);

		scene.geometries.add( //
				new Polygon(new Point3D(1500, 250, 0), new Point3D(1500, -250, 0), new Point3D(-1500, -250, 0),
						new Point3D(-1500, 250, 0)).setEmission(colorArk1).setMaterial(mat),
				new Polygon(new Point3D(1500, 250, 300), new Point3D(1500, -250, 300), new Point3D(-1500, -250, 300),
						new Point3D(-1500, 250, 300)).setEmission(colorArk1).setMaterial(mat),
				new Polygon(new Point3D(1499, 250, 0), new Point3D(1499, -250, 0), new Point3D(1499, -250, 300),
						new Point3D(1499, 250, 300)).setEmission(colorArkInsid).setMaterial(mat),
				new Polygon(new Point3D(-1500, 250, 0), new Point3D(-1500, -250, 0), new Point3D(-1500, -250, 300),
						new Point3D(-1500, 250, 300)).setEmission(colorArkInsid).setMaterial(mat),
				new Polygon(new Point3D(1499, 249, 0), new Point3D(1499, 249, 300), new Point3D(-1500, 249, 300),
						new Point3D(-1500, 249, 0)).setEmission(colorArkInsid).setMaterial(mat),
				new Polygon(new Point3D(1499, -249, 0), new Point3D(1499, -249, 300), new Point3D(-1500, -249, 300),
						new Point3D(-1500, -249, 0)).setEmission(colorArkInsid).setMaterial(mat), //

				new Polygon(new Point3D(1000, 200, 300), new Point3D(1000, -200, 300), new Point3D(-1000, -200, 300),
						new Point3D(-1000, 200, 300)).setEmission(colorTopArk).setMaterial(mat),
				new Polygon(new Point3D(1010, 210, 350), new Point3D(1010, -210, 350), new Point3D(-1100, -210, 350),
						new Point3D(-1100, 210, 350)).setEmission(colorTopArk).setMaterial(mat),
				new Polygon(new Point3D(1000, 200, 300), new Point3D(1000, -200, 300), new Point3D(1000, -200, 350),
						new Point3D(1000, 200, 350)).setEmission(colorTopArk).setMaterial(mat),
				new Polygon(new Point3D(-1000, 200, 300), new Point3D(-1000, -200, 300), new Point3D(-1000, -200, 350),
						new Point3D(-1000, 200, 350)).setEmission(colorTopArk).setMaterial(mat),
				new Polygon(new Point3D(1000, 200, 300), new Point3D(1000, 200, 350), new Point3D(-1000, 200, 350),
						new Point3D(-1000, 200, 300)).setEmission(colorTopArk).setMaterial(mat),
				new Polygon(new Point3D(1000, -200, 300), new Point3D(1000, -200, 350), new Point3D(-1000, -200, 350),
						new Point3D(-1000, -200, 300)).setEmission(colorTopArk).setMaterial(mat),

				new Polygon(new Point3D(1000.1, 25, 305), new Point3D(1000.1, -25, 305), new Point3D(1000.1, -25, 345),
						new Point3D(1000.1, 25, 345)).setEmission(new Color(188, 143, 143)).setMaterial(mat),
				new Polygon(new Point3D(1000.2, 20, 310), new Point3D(1000.2, -20, 310), new Point3D(1000.2, -20, 340),
						new Point3D(1000.2, 20, 340)).setEmission(new Color(192, 192, 192))
								.setMaterial(new Material().setKd(0.5).setKs(0.5).setKt(0.8).setShininess(60)),

				new Triangle(new Point3D(1010, 210, 350), new Point3D(1010, -210, 350), new Point3D(750, 0, 370))
						.setEmission(colorRoofArk).setMaterial(mat),
				new Triangle(new Point3D(-1010, 210, 350), new Point3D(-1010, -210, 350), new Point3D(-750, 0, 370))
						.setEmission(colorRoofArk).setMaterial(mat),
				new Polygon(new Point3D(1010, 210, 350), new Point3D(750, 0, 370), new Point3D(-750, 0, 370),
						new Point3D(-1010, 210, 350)).setEmission(colorRoofArk.add(new Color(100, 65, 30)))
								.setMaterial(mat),
				new Polygon(new Point3D(1010, -210, 350), new Point3D(750, 0, 370), new Point3D(-750, 0, 370),
						new Point3D(-1010, -210, 350)).setEmission(colorRoofArk.add(new Color(50, 30, 15)))
								.setMaterial(mat),
				new Plane(new Point3D(0, 0, -5000), new Vector(-0.1, 0, 3)).setEmission(colorSea).setMaterial(matSea),
				new Sphere(new Point3D(1500, 250, 20), 50).setEmission(colorSea).setMaterial(matSea));

		for (int y = -3000; y < 1000; y += 50)
			for (int x = -7000; x < 3000; x += 50) {
				double z = Math.sin(x) * Math.sin(y) * 60 + 40;
				scene.geometries.add(new Sphere(new Point3D(x, y, z), 25).setEmission(colorSea).setMaterial(matSea));
			}

		for (int z = 0; z <= 250; z += 50)
			for (int x = 1500; x >= -1250; x -= 250) {
				scene.geometries.add(new Polygon(new Point3D(x, 250, z), new Point3D(x, 250, z + 20),
						new Point3D(x - 240, 250, z + 20), new Point3D(x - 240, 250, z))
								.setEmission(colorArk.get((int) (Math.random() * 2))).setMaterial(mat));
				// new Polygon(new Point3D(x, -250, z), new Point3D(x, -250, z + 20),
				// new Point3D(x - 240, -250, z + 20), new Point3D(x - 240, -250, z))
				// .setEmission(colorArk.get((int) (Math.random() * 2))).setMaterial(mat));
			}
		for (int z = 25; z <= 275; z += 50)
			for (int x = 1500; x > -1500; x -= 150) {
				scene.geometries.add(new Polygon(new Point3D(x, 250, z), new Point3D(x, 250, z + 20),
						new Point3D(x - 140, 250, z + 20), new Point3D(x - 140, 250, z))
								.setEmission(colorArk.get((int) (Math.random() * 2))).setMaterial(mat));
				// new Polygon(new Point3D(x, -250, z), new Point3D(x, -250, z + 20),
				// new Point3D(x - 140, -250, z + 20), new Point3D(x - 140, -250, z))
				// .setEmission(colorArk.get((int) (Math.random() * 2))).setMaterial(mat));
			}
		for (int z = 0; z <= 275; z += 25) {
			scene.geometries.add(new Polygon(new Point3D(1500, 245, z), new Point3D(1500, 245, z + 20),
					new Point3D(1500, 250, z + 20), new Point3D(1500, 250, z))
							.setEmission(colorArk.get((int) (Math.random() * 2))).setMaterial(mat));
		}

		for (int z = 0; z <= 250; z += 50)
			for (int y = -250; y <= 150; y += 100) {
				scene.geometries.add(new Polygon(new Point3D(1500, y, z), new Point3D(1500, y, z + 20),
						new Point3D(1500, y + 93, z + 20), new Point3D(1500, y + 93, z))
								.setEmission(colorArk.get((int) (Math.random() * 2))).setMaterial(mat));
				// new Polygon(new Point3D(-1500, y, z), new Point3D(-1500, y, z + 20),
				// new Point3D(-1500, y + 93, z + 20), new Point3D(-1500, y + 93, z))
				// .setEmission(colorArk.get((int) (Math.random() * 2))).setMaterial(mat));
			}

		double c = 500 / 6;
		for (int z = 25; z <= 275; z += 50)
			for (int y = -250; y <= 5 * c - 250; y += c) {
				scene.geometries.add(new Polygon(new Point3D(1500, y, z), new Point3D(1500, y, z + 20),
						new Point3D(1500, y + c - 7, z + 20), new Point3D(1500, y + c - 7, z))
								.setEmission(colorArk.get((int) (Math.random() * 2))).setMaterial(mat));
				// new Point3D(-1500, y, z), new Point3D(-1500, y, z + 20),
				// new Point3D(-1500, y + c - 7, z + 20), new Point3D(-1500, y + c - 7, z))
				// .setEmission(colorArk.get((int) (Math.random() * 2))).setMaterial(mat));
			}

		scene.lights.add(new DirectionalLight(new Color(255, 229, 155), new Vector(-2, 1, -0.7)));
		scene.lights.add(new PointLight(new Color(255, 100, 50), new Point3D(1005, 0, 325)));

		ImageWriter imageWriter = new ImageWriter("NoahsArk", 1020, 1020);
		Render render = new Render() //
				.setCamera(camera) //
				.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracer(scene, 200)) //
				.setMultithreading(3).setDebugPrint(); //
				//.setAsMultyRays();
		render.renderImage();
		render.writeToImage();
	}

}
