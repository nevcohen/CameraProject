package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class FlashLight extends PointLight implements LightSource {

	private Vector direction;
	private int exp;

	public FlashLight(Color intensity, Point3D position, Vector direction, int exp) {
		super(intensity, position);
		if(exp<1)
			throw new IllegalArgumentException("exp less then 1");
		this.direction = direction.normalized();
		this.exp = exp;
		
	}

	@Override
	public Color getIntensity(Point3D p) {
		double dirL = direction.dotProduct(super.getL(p));
		if (dirL <= 0)
			return Color.BLACK;
		return super.getIntensity(p).scale(Math.pow(dirL, exp));
	}

	@Override
	public Vector getL(Point3D p) {
		return super.getL(p);
	}
}
