package elements;

import static primitives.Util.*;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class Camera {
	private Point3D location;
	private Vector vUp, vTo, vRight;
	private double widthVP, heightVP, distanceVP;

	/**
	 * @param location
	 * @param vUp
	 * @param vTo
	 */
	public Camera(Point3D location, Vector vTo, Vector vUp) {
		if (!isZero(vUp.dotProduct(vTo)))
			throw new IllegalArgumentException("Vectors are not perpendicular to each other");
		this.location = location;
		this.vUp = vUp.normalize();
		this.vTo = vTo.normalize();
		this.vRight = vTo.crossProduct(vUp).normalize();
	}

	public Point3D getLocation() {
		return location;
	}

	public Vector getvUp() {
		return vUp;
	}

	public Vector getvTo() {
		return vTo;
	}

	public Vector getvRight() {
		return vRight;
	}

	public Camera setViewPlaneSize(double width, double height) {
		if (width < 0 || isZero(width) || height < 0 && isZero(height))
			throw new IllegalArgumentException("The size of the view plane is invalid");
		widthVP = width;
		heightVP = height;
		return this;
	}

	public Camera setViewPlaneDistance(double distance) {
		if (distance < 0 || isZero(distance))
			throw new IllegalArgumentException("The distance of the view plane is invalid");
		distanceVP = distance;
		return this;
	}

	public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {
		return null;
	}
}
