package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Util;
import primitives.Vector;

/**
 * Flashlight is a light source that has a beam, which attenuates from the light
 * source and the distance from the source.
 */
public class SpotLight extends PointLight implements LightSource {

	/**
	 * Direction of the lights' beam
	 */
	private Vector direction;
	/**
	 * The exponent of the attenuation of the beam. As we get farther from the beam,
	 * the attenuation of the light will follow, until 0 in which there will be no
	 * light.
	 */
	private int exp = 1;

	/**
	 * Constructor that gets the intensity of the light, its' origins, the direction
	 * it goes at, and the exponent of the attenuation of the beam.
	 * 
	 * @param intensity of the beam
	 * @param position  of the beam
	 * @param direction of the beam
	 */
	public SpotLight(Color intensity, Point3D position, Vector direction) {
		super(intensity, position);
		this.direction = direction.normalized();
	}

	/**
	 * Resize the light beam of the flashlight.
	 * 
	 * @param exp - The exponent of resizing the beam
	 * @return The flashlight itself
	 */
	public SpotLight setExp(int exp) {
		if (exp < 1) // There is an assumption here, that a flash light narrows the beam. However,
			// this is malleable.
			throw new IllegalArgumentException("exp less then 1");
		this.exp = exp;
		return this;
	}

	@Override
	public Color getIntensity(Point3D p) {
		double dirL = Util.alignZero(direction.dotProduct(super.getL(p)));
		if (dirL <= 0)
			return Color.BLACK;
		if (exp != 1)
			dirL = Math.pow(dirL, exp);
		return super.getIntensity(p).scale(dirL);
	}
}
