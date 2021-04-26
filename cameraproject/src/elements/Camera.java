package elements;

import static primitives.Util.*;
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
	 * Camera direction - Vector upward of the camera
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
	 * A camera constructor, which gets location and two directions (and calculates
	 * the third direction), the constructor normalizes the direction vectors.
	 * 
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
	 * @param width of the view plane (Greater than zero)
	 * @param height of the view plane (Greater than zero)
	 * @return the camera itself 
	 */
	public Camera setViewPlaneSize(double width, double height) {
		if (width < 0 || isZero(width) || height < 0 && isZero(height))
			throw new IllegalArgumentException("The size of the view plane is invalid");
		widthVP = width;
		heightVP = height;
		return this;
	}

	/**
	 * Defines the distance of the view plane from the camera
	 * 
	 * @param distance of the view plane from the camera (Greater than zero)
	 * @return the camera itself 
	 */
	public Camera setViewPlaneDistance(double distance) {
		if (distance < 0 || isZero(distance))
			throw new IllegalArgumentException("The distance of the view plane is invalid");
		distanceVP = distance;
		return this;
	}

	/**
	 * Build a ray through a specific pixel in the view plane
	 * 
	 * @param nX - Number of columns in the view plane
	 * @param nY - Number of rows in the view plane
	 * @param j - The column number of the pixel
	 * @param i - The row number of the pixel
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
}
