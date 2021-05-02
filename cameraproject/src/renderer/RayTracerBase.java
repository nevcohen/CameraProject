package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * 
 */
public abstract class RayTracerBase {
	protected Scene scene;

	/**
	 * 
	 * @param scene
	 */
	public RayTracerBase(Scene scene) {
		this.scene = scene;
	}
	
	/**
	 * 
	 * 
	 * @param ray
	 * @return
	 */
	protected abstract Color traceRay(Ray ray);
}
