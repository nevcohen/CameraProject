package primitives;

/**
 * The material in which the object is made of, may effect the lighting of the
 * scene.
 */
public class Material {

	/**
	 * The diffuse coefficient.
	 */
	public double kD = 0;
	/**
	 * The specular coefficient.
	 */
	public double kS = 0;
	/**
	 * The shininess coefficient.
	 */
	public int nShininess = 0;

	/**
	 * The transparency coefficient.
	 */
	public double kT = 0;
	/**
	 * The reflection coefficient.
	 */
	public double kR = 0;

	/**
	 * Sets diffuse coefficient.
	 * 
	 * @param kD the kD to set
	 * @return The material itself that we changed
	 */
	public Material setKd(double kD) {
		this.kD = kD;
		return this;
	}

	/**
	 * Sets specular coefficient.
	 * 
	 * @param kS the kS to set
	 * @return The material itself that we changed
	 */
	public Material setKs(double kS) {
		this.kS = kS;
		return this;
	}

	/**
	 * Sets shininess coefficient.
	 * 
	 * @param nShininess the nShininess to set
	 * @return The material itself that we changed
	 */
	public Material setShininess(int nShininess) {
		this.nShininess = nShininess;
		return this;
	}

	/**
	 * Sets transparency coefficient.
	 * 
	 * @param kT the kT to set
	 * @return The material itself that we changed
	 */
	public Material setKt(double kT) {
		this.kT = kT;
		return this;
	}

	/**
	 * Sets reflection coefficient.
	 * 
	 * @param kR the kR to set
	 * @return The material itself that we changed
	 */
	public Material setKr(double kR) {
		this.kR = kR;
		return this;
	}
}
