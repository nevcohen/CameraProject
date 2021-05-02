package renderer;

import java.util.List;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

/**
 * 
 * 
 */
public class RayTracerBasic extends RayTracerBase {

	/**
	 * 
	 * 
	 * @param scene
	 */
	public RayTracerBasic(Scene scene) {
		super(scene);
	}

	/**
	 * 
	 * 
	 * @param point3d
	 * @return
	 */
	private Color calcColor(Point3D point3d) {
		return scene.ambientLight.getIntensity();
	}
	
	@Override
	protected Color traceRay(Ray ray) {
		List<Point3D> allPoint3ds = scene.geometries.findIntersections(ray);
		if(allPoint3ds == null)
			return scene.background;
		Point3D point3d = ray.findClosestPoint(allPoint3ds);		
		return calcColor(point3d);
	}

}