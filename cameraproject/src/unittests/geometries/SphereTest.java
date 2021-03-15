package unittests.geometries;

import static org.junit.Assert.*;
import geometries.*;
import primitives.*;

import org.junit.Test;

public class SphereTest {

	@Test
	public void testGetNormal() {
		Sphere s = new Sphere(new Point3D(1,1,1), 2);
		
		// ============ Equivalence Partitions Tests ==============	
		assertEquals("",s.getNormal(new Point3D(1,1,3)),new Vector(0,0,1));
	}

}
