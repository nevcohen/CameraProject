package renderer;

import java.util.MissingResourceException;

import elements.Camera;
import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * A class to render the picture, and create the image.
 */
public class Render {

	/**
	 * A variable used to export the image in the desired format
	 */
	ImageWriter imageWriter;
	/**
	 * Details of the scene used for the picture
	 */
	Scene scene;
	/**
	 * Details of the location of the camera
	 */
	Camera camera;
	/**
	 * Details of the tracer
	 */
	RayTracerBase rayTracerBase;

	/**
	 * A function designed to change the setting of the image writing process
	 * 
	 * @param imageWriter – the new writer we will base the render on
	 * @return the render including the new writer
	 */
	public Render setImageWriter(ImageWriter imageWriter) {
		this.imageWriter = imageWriter;
		return this;
	}

	/**
	 * A method to change the scene behind the desired picture.
	 * 
	 * @param scene – the new scene used for the picture
	 * @return the new render, including the updated scene.
	 */
	public Render setScene(Scene scene) {
		this.scene = scene;
		return this;
	}

	/**
	 * A method to change the camera, and/or its’ location, behind the desired
	 * picture.
	 * 
	 * @param camera – the new camera/camera angle used for the picture
	 * @return the new render, including the updated camera position.
	 */
	public Render setCamera(Camera camera) {
		this.camera = camera;
		return this;
	}

	/**
	 * A function to update the ray tracer.
	 * 
	 * @param rayTracerBase – the new ray tracer.
	 * @return the new renderer, including the updated ray tracer.
	 */
	public Render setRayTracer(RayTracerBase rayTracerBase) {
		this.rayTracerBase = rayTracerBase;
		return this;
	}

	/**
	 * A function used to take the image information, and render it into a picture.
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
	 * A function to prepare the base of the picture, a.k.a, the grid.
	 * 
	 * @param interval – the number used to set the size of the grid.
	 * @param color    – the color we want to print the grid in.
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
	 * A function that uses the imageWriter to generate the image
	 */
	public void writeToImage() {
		if (imageWriter == null)
			throw new MissingResourceException("", "Render", "ImageWriter");
		imageWriter.writeToImage();
	}

}