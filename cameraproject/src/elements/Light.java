/**
 * 
 */
package elements;

import primitives.Color;

/**
 * 
 */
abstract class Light {
	/**
	 * Original fill light of the scene
	 */
	private Color intensity;

	/**
	 * @param intensity
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
