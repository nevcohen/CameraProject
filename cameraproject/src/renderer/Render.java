package renderer;

import java.util.MissingResourceException;

import elements.Camera;
import primitives.Color;
import scene.Scene;

public class Render {

	ImageWriter imageWriter;
	Scene scene;
	Camera camera;
	RayTracerBase rayTracerBase;

	public Render setImageWriter(ImageWriter imageWriter) {
		this.imageWriter = imageWriter;
		return this;
	}

	public Render setScene(Scene scene) {
		this.scene = scene;
		return null;
	}

	public Render setCamera(Camera camera) {
		this.camera = camera;
		return null;
	}

	public Render setRayTracer(RayTracerBase rayTracerBase) {
		this.rayTracerBase = rayTracerBase;
		return null;
	}

	public void renderImage() {
		if(imageWriter == null || scene == null || camera == null || rayTracerBase == null)
			throw new MissingResourceException("", "Render", "");
		throw new UnsupportedOperationException();
	}

	public void printGrid(int i, Color color) {
		if(imageWriter == null)
			throw new MissingResourceException("", "Render", "ImageWriter");
	}

	public void writeToImage() {
		if(imageWriter == null)
			throw new MissingResourceException("", "Render", "ImageWriter");
	}

}
