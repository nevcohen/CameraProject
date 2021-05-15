package elements;

import primitives.Color;

/**
 * Ambient Light of the scene
 */
public class AmbientLight extends Light {

	/**
	 * Default constructor, intensity is BLACK.
	 */
	public AmbientLight() {
		super(Color.BLACK);
	}

	/**
	 * Constructor that receives the color that the body produces and promotes the
	 * attenuation of light
	 * 
	 * @param intensity Light intensity according to RGB components
	 * @param kA        The attenuation coefficient of light
	 */
	public AmbientLight(Color intensity, double kA) {
		super(intensity.scale(kA));
	}

}
