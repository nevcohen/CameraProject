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
	 * The radius of the aperture plane
	 */
	private double apertureRadius = 0;
	/**
	 * The distance between two points on the aperture plane
	 */
	private double apertureScale = 1;

	/**
	 * The distance between the view plane and the focal plane
	 */
	private double focalPlaneDistance = 0;

	/**
	 * The size of the grid we will create to "split" every pixel into, to send rays
	 * accordingly (X by X).
	 */
	private int pixelGridSize = 1;

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
	 * Defining the radius of the aperture (builder design template)
	 * 
	 * @param radius of the aperture (Greater or equal to zero)
	 * @return the camera itself
	 */
	public Camera setApertureRadius(double radius) {
		if (alignZero(radius) < 0)
			throw new IllegalArgumentException("The radius of the aperture is invalid");
		apertureRadius = radius;
		return this;
	}

	/**
	 * Defining the distance between two points on the aperture plane (builder
	 * design template).
	 * 
	 * @param t - The updated scaler (Greater or equal to one)
	 * @return the camera itself
	 */
	public Camera setApertureScale(double t) {
		if (t < 1)
			throw new IllegalArgumentException("The scale of the aperture is invalid");
		apertureScale = t;
		return this;
	}

	/**
	 * Defines the distance of the view plane from the camera.
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
	 * Defines the distance between the view plane and the focal plane.
	 * 
	 * @param distance of the focal plane from the view plane (Greater than zero)
	 * @return the camera itself
	 */
	public Camera setFocalPlaneDistance(double distance) {
		if (alignZero(distance) < 0)
			throw new IllegalArgumentException("The distance of the focal plane is invalid");
		focalPlaneDistance = distance;
		return this;
	}

	/**
	 * Defines the size of the grid we will create to "split" every pixel into.
	 * 
	 * @param size of the grid
	 * @return the camera itself
	 */
	public Camera setPixelGridSize(int size) {
		if (alignZero(size) < 1)
			throw new IllegalArgumentException("The size of the grid is invalid");
		pixelGridSize = size;
		return this;
	}

	/**
	 * Build a ray through a specific pixel in the view plane.
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
	 * Creates a list of rays for a specific pixel in the view plane.
	 * 
	 * @param nX - Number of columns in the view plane
	 * @param nY - Number of rows in the view plane
	 * @param j  - The column number of the pixel
	 * @param i  - The row number of the pixel
	 * @return Ray from the camera through the desired pixel
	 */
	public List<Ray> constructRaysThroughPixel(int nX, int nY, int j, int i) {
		double yI = alignZero((((nY - 1) / 2d) - i) * (heightVP / nY)),
				xJ = alignZero((j - (nX - 1) / 2d) * (widthVP / nX));

		Point3D pIJ = location.add(vTo.scale(distanceVP)); // center of the VP
		if (xJ != 0)
			pIJ = pIJ.add(vRight.scale(xJ));
		if (yI != 0)
			pIJ = pIJ.add(vUp.scale(yI));

		if (pixelGridSize == 1) {
			if (apertureRadius == 0)
				return List.of(new Ray(location, pIJ.subtract(location)));
			return focusRays(pIJ);
		}
		return pixelRays(pIJ);
	}

	/**
	 * Anti Aliasing - Creates all of the rays going through a certain pixel, based
	 * on the pixelGridSize.
	 * 
	 * @param pIJ - The center of the pixel
	 * @return A list of the rays going through the pixels
	 */
	public List<Ray> pixelRays(Point3D pIJ) {
		Vector vUpPixel = vUp.scale(1.0 / pixelGridSize); // A vector to move the target of the ray up
		Vector vRightPixel = vRight.scale(1.0 / pixelGridSize); // A vector to move the target of the ray to the right

		Vector startOfLine = vRightPixel.scale(-pixelGridSize); // A vector to return us to the far left, after moving
																// to the end of the line

		Point3D downLeft = pIJ.add(vUpPixel.scale((1 - pixelGridSize) / 2.0) //
				.add(vRightPixel.scale((1 - pixelGridSize) / 2.0))); // Start the target at the bottom left of the grid

		List<Ray> allRays = new LinkedList<Ray>();

		for (int y = 0; y < pixelGridSize; y++, downLeft = downLeft.add(vUpPixel)) {
			for (int x = 0; x < pixelGridSize; x++, downLeft = downLeft.add(vRightPixel)) {
				if (apertureRadius == 0) // If there is only one ray from the camera
					allRays.add(new Ray(location, downLeft.subtract(location)));
				else
					allRays.addAll(focusRays(downLeft));
			}
			downLeft = downLeft.add(startOfLine);
		}
		return allRays;
	}

	/**
	 * Depth of field - Creating all of the rays from the aperture for a part of the
	 * pixel, according to pixelGridSize.
	 * 
	 * @param pixelPoint - A point that is part of a pixel, according to
	 *                   pixelGridSize.
	 * @return List of rays from the aperture with the center of the focus being the
	 *         continuation of the given point on the focal plane.
	 */
	public List<Ray> focusRays(Point3D pixelPoint) {
		Ray mainRay = new Ray(location, pixelPoint.subtract(location)); // The ray to determine the center of the focus
		Point3D intersectionPoint = mainRay.getPoint(distanceVP + focalPlaneDistance); // The center of the focus
		double diameter = 2 * apertureRadius;
		Vector startOfLine = vRight.scale(-diameter * apertureScale - apertureScale); // A vector to return us to the
																						// far left, after moving to the
																						// end of the line

		Point3D downLeft = location.add(vUp.scale(-apertureRadius * apertureScale) //
				.add(vRight.scale(-apertureRadius * apertureScale))); // Start the head at the bottom left of the
																		// aperture

		List<Ray> allRays = new LinkedList<Ray>();

		double rr = apertureRadius * apertureRadius * apertureScale * apertureScale; // The radius squared, to check
																						// which points are in a
																						// reasonable distance from the
																						// center of aperture.

		for (int y = 0; y <= diameter; y++, downLeft = downLeft.add(vUp.scale(apertureScale))) {
			for (int x = 0; x <= diameter; x++, downLeft = downLeft.add(vRight.scale(apertureScale)))
				if (alignZero(downLeft.distanceSquared(location) - rr) <= 0 // if the base point is in the range of the
																			// updated aperture
						&& !(x == apertureRadius && y == apertureRadius)) // excluding the main ray
					allRays.add(new Ray(downLeft, intersectionPoint.subtract(downLeft)));
			downLeft = downLeft.add(startOfLine);
		}

		return allRays;
	}

}
