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
import renderer.ImageWriter;
import renderer.RayTracer;
import renderer.Render;
import scene.Scene;

public class NoahsArk {

	@Test
	public void test() {
		Camera camera = new Camera(new Point3D(2500, 500, 600), new Vector(-10, -4, -3),
				new Vector(-10, -4, 116.0 / 3.0)) //
						.setViewPlaneDistance(1000).setViewPlaneSize(800, 800).setAperture(20, 8)
						.setFocalPlaneDistance(1800);
		Scene scene = new Scene("Test scene").setAmbientLight(new AmbientLight(new Color(java.awt.Color.GRAY), 0.2));

		Color colorArk = new Color(160, 82, 45);
		Color colorTopArk = new Color(139, 69, 19);
		Color colorRoofArk = new Color(205, 133, 63);
		Color colorSea1 = new Color(32, 178, 170);
		Color colorSea2 = new Color(0, 128, 128);
		Color colorSea3 = new Color(0, 206, 209);
		Color colorSea4 = new Color(95, 158, 160);
		List<Color> seaColors = List.of(colorSea1, colorSea2, colorSea3, colorSea4);
		Material mat = new Material().setKd(0.5).setKs(0.5).setShininess(60);

		scene.geometries.add( //
				new Polygon(new Point3D(1500, 250, 0), new Point3D(1500, -250, 0), new Point3D(-1500, -250, 0),
						new Point3D(-1500, 250, 0)).setEmission(colorArk).setMaterial(mat),
				new Polygon(new Point3D(1500, 250, 300), new Point3D(1500, -250, 300), new Point3D(-1500, -250, 300),
						new Point3D(-1500, 250, 300)).setEmission(colorArk).setMaterial(mat),
				new Polygon(new Point3D(1500, 250, 0), new Point3D(1500, -250, 0), new Point3D(1500, -250, 300),
						new Point3D(1500, 250, 300)).setEmission(colorArk).setMaterial(mat),
				new Polygon(new Point3D(-1500, 250, 0), new Point3D(-1500, -250, 0), new Point3D(-1500, -250, 300),
						new Point3D(-1500, 250, 300)).setEmission(colorArk).setMaterial(mat),
				new Polygon(new Point3D(1500, 250, 0), new Point3D(1500, 250, 300), new Point3D(-1500, 250, 300),
						new Point3D(-1500, 250, 0)).setEmission(colorArk).setMaterial(mat),
				new Polygon(new Point3D(1500, -250, 0), new Point3D(1500, -250, 300), new Point3D(-1500, -250, 300),
						new Point3D(-1500, -250, 0)).setEmission(colorArk).setMaterial(mat), //

				new Polygon(new Point3D(1000, 200, 300), new Point3D(1000, -200, 300), new Point3D(-1000, -200, 300),
						new Point3D(-1000, 200, 300)).setEmission(colorTopArk).setMaterial(mat),
				new Polygon(new Point3D(1010, 210, 350), new Point3D(1010, -210, 350), new Point3D(-1100, -210, 350),
						new Point3D(-1100, 210, 350)).setEmission(colorTopArk).setMaterial(mat),
				new Polygon(new Point3D(1000, 200, 300), new Point3D(1000, -200, 300), new Point3D(1000, -200, 350),
						new Point3D(1000, 200, 350)).setEmission(colorTopArk).setMaterial(mat),
				new Polygon(new Point3D(-1000, 200, 300), new Point3D(-1000, -200, 300), new Point3D(-1000, -200, 350),
						new Point3D(-1000, 200, 350)).setEmission(colorTopArk).setMaterial(mat),
				new Polygon(new Point3D(1000, 200, 300), new Point3D(1000, 200, 310), new Point3D(-1000, 200, 350),
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
				new Plane(Point3D.ZERO, new Vector(0, 0, 1)).setEmission(colorSea1).setMaterial(mat));

		Color colorSeaX;
		double randY, randX, randZ;
		for (int i = 0; i < 2000; i++) {
			int cI = (int) (Math.random() * 4);
			colorSeaX = seaColors.get(cI);
			randY = (Math.random() * (4001)) - 2000;
			randX = (Math.random() * (5001)) - 2500;
			randZ = (Math.random() * (40)) - 20;
			scene.geometries
					.add(new Sphere(new Point3D(randX, randY, randZ), 80).setEmission(colorSeaX).setMaterial(mat));
			randY = (Math.random() * (4001)) - 2000;
			randX = (Math.random() * (5001)) - 2500;
			randZ = (Math.random() * (25)) - 12.5;
			scene.geometries
					.add(new Sphere(new Point3D(randX, randY, randZ), 50).setEmission(colorSeaX).setMaterial(mat));
			randY = (Math.random() * (4001)) - 2000;
			randX = (Math.random() * (5001)) - 2500;
			randZ = (Math.random() * (20)) - 10;
			scene.geometries
					.add(new Sphere(new Point3D(randX, randY, randZ), 40).setEmission(colorSeaX).setMaterial(mat));
		}

		scene.lights.add(new DirectionalLight(new Color(250, 120, 120), new Vector(-1, 0.5, -2)));

		ImageWriter imageWriter = new ImageWriter("NoahsArk", 1020, 1020);
		Render render = new Render() //
				.setCamera(camera) //
				.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracer(scene, 200)) //
				.setMultithreading(3).setDebugPrint(); //
		// .setAsMultyRays();
		render.renderImage();
		render.writeToImage();
	}

}
