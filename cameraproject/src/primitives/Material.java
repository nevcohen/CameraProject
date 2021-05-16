package primitives;

public class Material {
	public double kD, kS;
	public int nShininess;

	/**
	 * 
	 */
	public Material() {
		this.kD = 0;
		this.kS = 0;
		this.nShininess = 0;
	}

	/**
	 * @param kD the kD to set
	 */
	public Material setKd(double kD) {
		this.kD = kD;
		return this;
	}

	/**
	 * @param kS the kS to set
	 */
	public Material setKs(double kS) {
		this.kS = kS;
		return this;
	}

	/**
	 * @param nShininess the nShininess to set
	 */
	public Material setShininess(int nShininess) {
		this.nShininess = nShininess;
		return this;
	}

}
