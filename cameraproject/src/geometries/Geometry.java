package geometries;

import primitives.Point3D;
import primitives.Vector;

public interface Geometry {

	public Vector getNormal(Point3D point); // returns the normal to the surface
}
