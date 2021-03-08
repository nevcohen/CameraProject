package geometries;

import primitives.Point3D;

public class Triangle extends Polygon {
	public Triangle(Point3D point1,Point3D point2,Point3D point3) { // Ctor for the triangle: puts 3 (and only 3) points into the Polygon base.
		super(point1,point2,point3);
	}
/*
	@Override
	public String toString() {// the tostring of polygon returns the entire list. so there is no do anything different here.
		return super.toString();
	}
	*/
}
