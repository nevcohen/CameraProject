/**
 * 
 */
package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * 
 */
public class PointLight extends Light implements LightSource {

	private Point3D position;
	private double kC, kL, kQ;

	public PointLight(Color intensity, Point3D position) {
		super(intensity);
		this.position = position;
		kC = 1;
		kL = 0;
		kQ = 0;
	}

	/**
	 * @param kC the kC to set
	 */
	public PointLight setKc(double kC) {
		this.kC = kC;
		return this;
	}

	/**
	 * @param kL the kL to set
	 */
	public PointLight setKl(double kL) {
		this.kL = kL;
		return this;
	}

	/**
	 * @param kQ the kQ to set
	 */
	public PointLight setKq(double kQ) {
		this.kQ = kQ;
		return this;
	}

	@Override
	public Color getIntensity(Point3D p) {
		double distanceSq = p.distanceSquared(position);
		double denominator = kQ * distanceSq + kL * Math.sqrt(distanceSq) + kC;
		return getIntensity().reduce(denominator);
	}

	@Override
	public Vector getL(Point3D p) {
		return p.subtract(position).normalize();
	}

}
