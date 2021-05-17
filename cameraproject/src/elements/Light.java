/**
 * 
 */
package elements;

import primitives.Color;

/**
 * An abstract class to provide the base of a light source
 */
abstract class Light {
	/**
	 * Original fill light of the scene
	 */
	private Color intensity;

	/**
	 * Constructor to set the intensity of the light source
	 * 
	 * @param intensity - The strength\intensity of the light
	 */
	protected Light(Color intensity) {
		this.intensity = intensity;
	}

	/**
	 * Gets the color intensity of the scene
	 * 
	 * @return the intensity
	 */
	public Color getIntensity() {
		return intensity;
	}

}
