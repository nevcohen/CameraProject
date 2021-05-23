/**
 * 
 */
package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * A class to hold a light source that originates at a given point.
 */
public class PointLight extends Light implements LightSource {

	/**
	 * The position in which the light originates.
	 */
	private Point3D position;
	/**
	 * The constant attenuation of the light.
	 */
	private double kC = 1;
	/**
	 * The linear attenuation of the light.
	 */
	private double kL = 0;
	/**
	 * The squared attenuation of the light.
	 */
	private double kQ = 0;

	/**
	 * A constructor that gets a base point, and assumes a constant attenuation of
	 * 1, at a given intensity.
	 * 
	 * @param intensity of the light
	 * @param position  in which the light originates
	 */
	public PointLight(Color intensity, Point3D position) {
		super(intensity);
		this.position = position;
	}

	/**
	 * Set the value of the constant attenuation variable (builder)
	 * 
	 * @param kC the kC to set
	 */
	public PointLight setKc(double kC) {
		this.kC = kC;
		return this;
	}

	/**
	 * Set the value of the linear attenuation variable (builder)
	 * 
	 * @param kL the kL to set
	 */
	public PointLight setKl(double kL) {
		this.kL = kL;
		return this;
	}

	/**
	 * Set the value of the squared attenuation variable (builder)
	 * 
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

	@Override
	public double getDistance(Point3D point) {
		return position.distance(point);
	}
}
