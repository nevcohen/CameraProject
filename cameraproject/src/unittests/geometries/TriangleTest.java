package unittests.geometries;

import static org.junit.Assert.*;

import org.junit.Test;

import geometries.Polygon;
import geometries.Triangle;
import primitives.Point3D;

public class TriangleTest {

	@Test
	public void testGetNormal() {
		Triangle testSubject = new Triangle(
				new Point3D(1,0,0),
				new Point3D(0,1,0),
				new Point3D(0,0,1));
		Vector projectedNormal = 
				
	}

}
