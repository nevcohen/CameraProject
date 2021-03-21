package unittests.geometries;

import static org.junit.Assert.*;

import org.junit.Test;

import geometries.Polygon;
import geometries.Triangle;
import primitives.Point3D;
import primitives.Vector;

public class TriangleTest {

	@Test
	public void testGetNormal() {
		Triangle newTriangle  = new Triangle(new Point3D(1,1,1),new Point3D(3,0,1),new Point3D(6,0,0));
		double x = Math.sqrt(14);
		assertEquals("Problematic triangle",new Vector(new Point3D(1/x,2/x,3/x)),newTriangle.getNormal(new Point3D(0,0,0)));
				
	}

}
