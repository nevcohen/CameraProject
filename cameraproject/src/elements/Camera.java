package elements;

import static primitives.Util.*;

import java.util.LinkedList;
import java.util.List;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * Class for the construction of the camera and the view plane of the project
 */
public class Camera {
	/**
	 * Location of the camera in space
	 */
	private Point3D location;

	/**
	 * Camera direction - vector upward of the camera
	 */
	private Vector vUp;
	/**
	 * Camera direction - vector to the front of the camera
	 */
	private Vector vTo;
	/**
	 * Camera direction - vector to the right of the camera
	 */
	private Vector vRight;

	/**
	 * The width of the view plane
	 */
	private double widthVP;
	/**
	 * The height of the view plane
	 */
	private double heightVP;
	/**
	 * The distance of the view plane from the Camera
	 */
	private double distanceVP;

	/**
	 * ----------------
	 */
	private double apertureRadius = 0;
	/**
	 * ----------------
	 */
	private double apertureScale = 1;

	/**
	 * -----------------
	 */
	private double focalPlaneDistance = 0;

	/**
	 * -----------------
	 */
	private int pixelGridLength = 1;

	/**
	 * A camera constructor, which gets location and two directions (and calculates
	 * the third direction), the constructor normalizes the direction vectors.
	 * 
	 * @param location of the camera in space
	 * @param vUp      Vector upward of the camera
	 * @param vTo      vector to the front of the camera
	 */
	public Camera(Point3D location, Vector vTo, Vector vUp) {
		if (!isZero(vUp.dotProduct(vTo)))
			throw new IllegalArgumentException("Vectors are not perpendicular to each other");
		this.location = location;
		this.vUp = vUp.normalize();
		this.vTo = vTo.normalize();
		this.vRight = vTo.crossProduct(vUp).normalize();
	}

	/**
	 * Gets the location of the camera in space (x,y,z)
	 * 
	 * @return Point3D of the location
	 */
	public Point3D getLocation() {
		return location;
	}

	/**
	 * Gets the vector upward of the camera
	 * 
	 * @return Vector vUp of the camera
	 */
	public Vector getvUp() {
		return vUp;
	}

	/**
	 * Gets the vector to the front of the camera
	 * 
	 * @return Vector vTo of the camera
	 */
	public Vector getvTo() {
		return vTo;
	}

	/**
	 * Gets the vector to the right of the camera
	 * 
	 * @return Vector vRight of the camera
	 */
	public Vector getvRight() {
		return vRight;
	}

	/**
	 * Defining the length and width of the view plane (builder design template)
	 * 
	 * @param width  of the view plane (Greater than zero)
	 * @param height of the view plane (Greater than zero)
	 * @return the camera itself
	 */
	public Camera setViewPlaneSize(double width, double height) {
		if (alignZero(width) < 0 || alignZero(height) < 0)
			throw new IllegalArgumentException("The size of the view plane is invalid");
		widthVP = width;
		heightVP = height;
		return this;
	}

	/**
	 * --------------------
	 * 
	 * @param width
	 * @param height
	 * @return
	 */
	public Camera setApertureRadius(double radius) {
		if (alignZero(radius) < 0)
			throw new IllegalArgumentException("The size of the aperture is invalid");
		apertureRadius = radius;
		return this;
	}
	
	/**
	 * -----------------
	 * 
	 * @param t
	 * @return
	 */
	public Camera setApertureScale(double t) {
		if (alignZero(t) < 1)
			throw new IllegalArgumentException("The scale of the aperture is invalid");
		apertureScale = t;
		return this;
	}

	/**
	 * Defines the distance of the view plane from the camera
	 * 
	 * @param distance of the view plane from the camera (Greater than zero)
	 * @return the camera itself
	 */
	public Camera setViewPlaneDistance(double distance) {
		if (alignZero(distance) <= 0)
			throw new IllegalArgumentException("The distance of the view plane is invalid");
		distanceVP = distance;
		return this;
	}

	/**
	 * ---------------------
	 * 
	 * @param distance
	 * @return
	 */
	public Camera setFocalPlaneDistance(double distance) {
		if (alignZero(distance) < 0)
			throw new IllegalArgumentException("The distance of the focal plane is invalid");
		focalPlaneDistance = distance;
		return this;
	}

	public Camera setPixelGridLength(int length) {
		if (alignZero(length) < 1)
			throw new IllegalArgumentException("The length of the grid is invalid");
		pixelGridLength = length;
		return this;
	}

	/**
	 * Build a ray through a specific pixel in the view plane
	 * 
	 * @param nX - Number of columns in the view plane
	 * @param nY - Number of rows in the view plane
	 * @param j  - The column number of the pixel
	 * @param i  - The row number of the pixel
	 * @return Ray from the camera through the desired pixel
	 */
	public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {
		double yI = alignZero((((nY - 1) / 2d) - i) * (heightVP / nY)),
				xJ = alignZero((j - (nX - 1) / 2d) * (widthVP / nX));

		Point3D pIJ = location.add(vTo.scale(distanceVP)); // center of the VP
		if (xJ != 0)
			pIJ = pIJ.add(vRight.scale(xJ));
		if (yI != 0)
			pIJ = pIJ.add(vUp.scale(yI));

		return new Ray(location, pIJ.subtract(location));
	}

	/**
	 * --------------------
	 * 
	 * @param nX
	 * @param nY
	 * @param j
	 * @param i
	 * @return
	 */
	public List<Ray> constructRaysThroughPixel(int nX, int nY, int j, int i) {
		double yI = alignZero((((nY - 1) / 2d) - i) * (heightVP / nY)),
				xJ = alignZero((j - (nX - 1) / 2d) * (widthVP / nX));

		Point3D pIJ = location.add(vTo.scale(distanceVP)); // center of the VP
		if (xJ != 0)
			pIJ = pIJ.add(vRight.scale(xJ));
		if (yI != 0)
			pIJ = pIJ.add(vUp.scale(yI));

		if (pixelGridLength == 1) {
			if (apertureRadius == 0)
				return List.of(new Ray(location, pIJ.subtract(location)));
			return focusRays(pIJ);
		}
		return pixelRays(pIJ);
	}

	private List<Ray> pixelRays(Point3D pIJ) {
		Vector vUpPixel = vUp.scale(1.0 / pixelGridLength);
		Vector vRightPixel = vRight.scale(1.0 / pixelGridLength);

		Vector startOfLine = vRightPixel.scale(-pixelGridLength);

		Point3D downLeft = pIJ
				.add(vUpPixel.scale((1 - pixelGridLength) / 2.0).add(vRightPixel.scale((1 - pixelGridLength) / 2.0)));

		List<Ray> allRays = new LinkedList<Ray>();

		for (int y = 0; y < pixelGridLength; y++, downLeft = downLeft.add(vUpPixel)) {
			for (int x = 0; x < pixelGridLength; x++, downLeft = downLeft.add(vRightPixel)) {
				if (apertureRadius == 0)
					allRays.add(new Ray(location, downLeft.subtract(location)));
				else
					allRays.addAll(focusRays(downLeft));
			}
			downLeft = downLeft.add(startOfLine);
		}
		return allRays;
	}

	/**
	 * ---------------
	 * 
	 * @param intersectionPoint
	 * @return
	 */
	private List<Ray> focusRays(Point3D pixelPoint) {
		Ray mainRay = new Ray(location, pixelPoint.subtract(location));
		Point3D intersectionPoint = mainRay.getPoint(distanceVP + focalPlaneDistance);
		double r = 2 * apertureRadius;
		Vector startOfLine = vRight.scale(-r*apertureScale);

		Point3D downLeft = location.add(vUp.scale(-apertureRadius*apertureScale).add(vRight.scale(-apertureRadius*apertureScale)));

		List<Ray> allRays = new LinkedList<Ray>();

		double rr = apertureRadius * apertureRadius * apertureScale * apertureScale;
		for (int y = 0; y < r; y++, downLeft = downLeft.add(vUp.scale(apertureScale))) {
			for (int x = 0; x < r; x++, downLeft = downLeft.add(vRight.scale(apertureScale)))
				if (alignZero(downLeft.distanceSquared(location) - rr) <= 0
						|| !(x == apertureRadius && y == apertureRadius))
					allRays.add(new Ray(downLeft, intersectionPoint.subtract(downLeft)));
			downLeft = downLeft.add(startOfLine);
		}

		return allRays;
	}

}
