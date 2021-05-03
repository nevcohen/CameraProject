package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * An abstract class that will follow a Ray in our scene and find colors for
 * pixels
 */
public abstract class RayTracerBase {
	protected Scene scene;

	/**
	 * A constructor who gets the scene
	 * 
	 * @param scene of the virtual world we have built
	 */
	public RayTracerBase(Scene scene) {
		this.scene = scene;
	}

	/**
	 * The function receives a Rùè and returns the color of the first point that
	 * the beam strikes
	 * 
	 * @param ray that we will follow to find the color
	 * @return The color that will color the pixel from which the Ray was sent
	 */
	protected abstract Color traceRay(Ray ray);
}
