package unittests.geometries;

import static org.junit.Assert.*;

import org.junit.Test;

import geometries.Plane;
import primitives.Point3D;
import primitives.Vector;

public class PlaneTest {

	@Test
	public void testGetNormalPoint3D() { 
		Plane p = new Plane(new Point3D(1,1,1),new Vector(1,0,0));
		assertEquals("Mistake found with GetNormal",p.getNormal(new Point3D(7,-2,1)),new Vector(1,0,0));
	}

}
