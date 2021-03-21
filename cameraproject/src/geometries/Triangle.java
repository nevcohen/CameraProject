package geometries;

import primitives.*;

/**
 * Triangle class - Represented by the three vertices (3 Points3D)
 */
public class Triangle extends Polygon {
	
	/**
	 * Ctor for the triangle: puts 3 (and only 3) points into the Polygon base.
	 * @param Point3D - The three vertices of the triangle
	 */
	public Triangle(Point3D point1,Point3D point2,Point3D point3) {
		super(point1,point2,point3);
	}
	

	
	/*
	 * 	
	 * The tostring of polygon, so there is no do anything different here.
	 * @return the entire list of point
	 * 
	 * @Override
     * public String toString() {
	 *	return super.toString();
	 * }
	 */

}
