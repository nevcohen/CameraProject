package renderer;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

public class RayTracerBasic extends RayTracerBase {

	public RayTracerBasic(Scene scene) {
		super(scene);
	}

	private Color calcColor(Point3D point3d) {
		return scene.ambientLight.getIntensity();
	}
	
	@Override
	protected Color traceRay(Ray ray) {
		return null;
	}

}