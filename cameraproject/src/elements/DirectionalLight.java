package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * A class to hold a directional light, aka a light source from a given
 * direction that doesn't attenuate.
 */
public class DirectionalLight extends Light implements LightSource {

	/**
	 * The direction the light comes from
	 */
	private Vector direction;

	/**
	 * Constructor for the class directional light, sets the intensity and the
	 * direction of the light source.
	 * 
	 * @param intensity of the light source
	 * @param direction of the light source
	 */
	public DirectionalLight(Color intensity, Vector direction) {
		super(intensity);
		this.direction = direction.normalized();
	}

	@Override
	public Color getIntensity(Point3D p) {
		return getIntensity();
	}

	@Override
	public Vector getL(Point3D p) {
		return direction;
	}
	
	@Override
	public double getDistance(Point3D point) {
		return Double.POSITIVE_INFINITY;
	}
}
