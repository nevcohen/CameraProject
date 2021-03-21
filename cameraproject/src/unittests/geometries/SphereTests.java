package unittests.geometries;

import static org.junit.Assert.*;

import org.junit.Test;

import geometries.Plane;
import geometries.Sphere;
import primitives.Point3D;
import primitives.Vector;

public class SphereTests {

	@Test
	public void testGetNormal() {
		Sphere s = new Sphere(new Point3D(1,1,1), 2);
		
		// ============ Equivalence Partitions Tests ==============	
		assertEquals("",s.getNormal(new Point3D(1,1,3)),new Vector(0,0,1));
	}


}
