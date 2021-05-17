/**
 * 
 */
package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * An interface to have functions regarding the intensity of the light source
 * and the vector it sends.
 */
public interface LightSource {
	/**
	 * A function to return the lights intensity at a given point.
	 * 
	 * @param p - The point we want the light's intensity of.
	 * @return the intensity of the light source at the given point
	 */
	public Color getIntensity(Point3D p);

	/**
	 * A function that returns a vector in which a light hits a given point.
	 * 
	 * @param p - The point we want to know the vector of, as the light hits it.
	 * @return the vector of the light as it hits the given point.
	 */
	public Vector getL(Point3D p);

}
