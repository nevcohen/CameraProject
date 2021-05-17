/**
 * 
 */
package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * 
 */
public class SpotLight extends FlashLight implements LightSource {

	public SpotLight(Color intensity, Point3D position, Vector direction) {
		super(intensity, position, direction, 1);
	}

	@Override
	public Color getIntensity(Point3D p) {
		return super.getIntensity(p);
	}

	@Override
	public Vector getL(Point3D p) {
		return super.getL(p);
	}

}
