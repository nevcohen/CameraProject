package elements;

import primitives.Color;

/**
 * 
 * 
 */
public class AmbientLight {

	private Color intensity;

	/**
	 * 
	 * 
	 * @param intensity
	 */
	public AmbientLight(Color intensity, double kA) {
		this.intensity = intensity.scale(kA);
	}

	/**
	 * 
	 * 
	 * @return
	 */
	public Color getIntensity() {
		return intensity;
	}

}
