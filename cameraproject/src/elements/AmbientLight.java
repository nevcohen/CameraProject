package elements;

import primitives.Color;

/**
 * Ambient Light of the scene
 */
public class AmbientLight {

	/**
	 * Original fill light of the scene
	 */
	private Color intensity;

	/**
	 * Constructor that receives the color that the body produces and promotes the
	 * attenuation of light
	 * 
	 * @param intensity Light intensity according to RGB components
	 * @param kA        The attenuation coefficient of light
	 */
	public AmbientLight(Color intensity, double kA) {
		this.intensity = intensity.scale(kA);
	}

	/**
	 * Gets the color intensity of the scene
	 * 
	 * @return intensity
	 */
	public Color getIntensity() {
		return intensity;
	}

}
