package primitives;

/**
 * The material in which the object is made of, may effect the lighting of the
 * scene.
 */
public class Material {

	/**
	 * The diffuse coefficient.
	 */
	public double kD;
	/**
	 * The specular coefficient.
	 */
	public double kS;
	/**
	 * The shininess coefficient.
	 */
	public int nShininess;

	/**
	 * Constructor that sets the various coefficients to 0.
	 */
	public Material() {
		this.kD = 0;
		this.kS = 0;
		this.nShininess = 0;
	}

	/**
	 * Sets diffuse coefficient.
	 * 
	 * @param kD the kD to set
	 */
	public Material setKd(double kD) {
		this.kD = kD;
		return this;
	}

	/**
	 * Sets specular coefficient.
	 * 
	 * @param kS the kS to set
	 */
	public Material setKs(double kS) {
		this.kS = kS;
		return this;
	}

	/**
	 * Sets shininess coefficient.
	 * 
	 * @param nShininess the nShininess to set
	 */
	public Material setShininess(int nShininess) {
		this.nShininess = nShininess;
		return this;
	}

}
