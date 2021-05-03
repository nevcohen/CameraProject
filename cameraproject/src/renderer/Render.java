package renderer;

import java.util.MissingResourceException;

import elements.Camera;
import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * 
 */
public class Render {

	/**
	 * 
	 */
	ImageWriter imageWriter;
	/**
	 * 
	 */
	Scene scene;
	/**
	 * 
	 */
	Camera camera;
	/**
	 * 
	 */
	RayTracerBase rayTracerBase;

	/**
	 * 
	 * 
	 * @param imageWriter
	 * @return
	 */
	public Render setImageWriter(ImageWriter imageWriter) {
		this.imageWriter = imageWriter;
		return this;
	}

	/**
	 * 
	 * 
	 * @param scene
	 * @return
	 */
	public Render setScene(Scene scene) {
		this.scene = scene;
		return this;
	}

	/**
	 * 
	 * 
	 * @param camera
	 * @return
	 */
	public Render setCamera(Camera camera) {
		this.camera = camera;
		return this;
	}

	/**
	 * 
	 * 
	 * @param rayTracerBase
	 * @return
	 */
	public Render setRayTracer(RayTracerBase rayTracerBase) {
		this.rayTracerBase = rayTracerBase;
		return this;
	}

	/**
	 * 
	 */
	public void renderImage() {
		if (imageWriter == null || scene == null || camera == null || rayTracerBase == null)
			throw new MissingResourceException("", "Render", "");
		int nX = imageWriter.getNx();
		int nY = imageWriter.getNy();
		Color color;
		RayTracerBasic rayTracerBasic;
		Ray ray;
		for (int x = 0; x < nX; x++)
			for (int y = 0; y < nY; y++) {
				rayTracerBasic = new RayTracerBasic(scene);
				ray = camera.constructRayThroughPixel(nX, nY, x, y);
				color = rayTracerBasic.traceRay(ray);
				imageWriter.writePixel(x, y, color);
			}
	}

	/**
	 * 
	 * 
	 * @param interval
	 * @param color
	 */
	public void printGrid(int interval, Color color) {
		if (imageWriter == null)
			throw new MissingResourceException("", "Render", "ImageWriter");
		for (int x = 0; x < imageWriter.getNx(); x++)
			for (int y = 0; y < imageWriter.getNy(); y++) {
				if (y % interval == 0 || x % interval == 0)
					imageWriter.writePixel(x, y, color);
			}
	}

	/**
	 * 
	 */
	public void writeToImage() {
		if (imageWriter == null)
			throw new MissingResourceException("", "Render", "ImageWriter");
		imageWriter.writeToImage();
	}

}
