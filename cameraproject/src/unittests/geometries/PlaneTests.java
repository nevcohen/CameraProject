package unittests.geometries;

import static org.junit.Assert.*;

import org.junit.Test;

import geometries.Plane;
import primitives.Point3D;
import primitives.Vector;

public class PlaneTests {

	@Test
	public void testGetNormalPoint3D() {
		Plane p = new Plane(new Point3D(1,1,1),new Vector(1,0,0));
		assertEquals("",p.getNormal(new Point3D(1,2,3)),new Vector(1,1,1));
	}

}
