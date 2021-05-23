package unittests.elements;

import org.junit.Test;

import elements.AmbientLight;
import elements.Camera;
import elements.DirectionalLight;
import elements.SpotLight;
import geometries.Polygon;
import geometries.Sphere;
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
		Camera camera = new Camera(new Point3D(750, 0, 850), new Vector(-7.5, 0, -8.5), new Vector(-8.5, 0, 7.5)) //
				.setViewPlaneSize(200, 200).setViewPlaneDistance(1000);

		Scene scene = new Scene("Test scene");
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

		scene.geometries.add( //
				new Polygon(new Point3D(0, 100, 0), new Point3D(0, -100, 0), new Point3D(-40, -100, 100),
						new Point3D(-40, 100, 100)).setEmission(new Color(76.5, 102, 127.5))
								.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
				new Polygon(new Point3D(0, 100, 0), new Point3D(150, 100, 0), new Point3D(150, -100, 0),
						new Point3D(0, -100, 0)).setEmission(new Color(76.5, 102, 127.5))
								.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)));

		scene.lights.add(new DirectionalLight(new Color(153, 102, 127.5), new Vector(0, 0, -1)));

		ImageWriter imageWriter = new ImageWriter("firstPic", 600, 600);
		Render render = new Render() //
				.setImageWriter(imageWriter) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));

		render.renderImage();
		render.writeToImage();
	}

}
