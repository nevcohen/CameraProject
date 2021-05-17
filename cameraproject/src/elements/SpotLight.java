/**
 * 
 */
package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * A type of flashlight in which the light source is not affected by the
 * attenuation from the beam itself, having the exponent of the beams
 * attenuation 1.
 */
public class SpotLight extends FlashLight implements LightSource {

	/**
	 * A constructor that gets the lights' intensity, location, and the general
	 * direction.
	 * 
	 * @param intensity of the light
	 * @param position  of the light
	 * @param direction of the light
	 */
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
