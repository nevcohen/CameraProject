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
public class SpotLight extends PointLight implements LightSource {

	private Vector direction;

	public SpotLight(Color intensity, Point3D position, Vector direction) {
		super(intensity, position);
		this.direction = direction.normalized();
	}

	@Override
	public Color getIntensity(Point3D p) {
		double dirL = direction.dotProduct(getL(p));
		if (dirL <= 0)
			return Color.BLACK;
		return super.getIntensity(p).scale(dirL);
	}

	@Override
	public Vector getL(Point3D p) {
		return super.getL(p);
	}

}
