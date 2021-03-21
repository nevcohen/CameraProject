package unittests.geometries;

import static org.junit.Assert.*;

import org.junit.Test;

import geometries.Tube;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class TubeTest {

	@Test
	public void testGetNormal() {
		Tube tube = new Tube(new Ray(new Point3D(0, 0, 2), new Vector(new Point3D(0, 0, 1))), 2);
		
		// ============ Equivalence Partitions Tests ==============	
		assertEquals("",tube.getNormal(new Point3D(0,2,0)),new Vector(0,1,0));
		
		assertEquals("",tube.getNormal(new Point3D(0,2,2)),new Vector(0,1,0));//-------
	}

}
