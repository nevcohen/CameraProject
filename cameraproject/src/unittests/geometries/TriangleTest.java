package unittests.geometries;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import geometries.*;
import primitives.*;

/**
 * Testing Triangles
 */
public class TriangleTest {

	/**
	 * Test method for {@link geometries.Polygon#getNormal(primitives.Point3D)}.
	 */
	@Test
	public void testGetNormal() {
		Triangle newTriangle = new Triangle(new Point3D(1, 1, 1), new Point3D(3, 0, 1), new Point3D(6, 0, 0));
		double x = Math.sqrt(14);
		assertEquals("Problematic triangle",new Vector(new Point3D(1/x,2/x,3/x)),newTriangle.getNormal(new Point3D(0,0,0)));
		// ============ Equivalence Partitions Tests ==============
		
		// TC01: There is a simple single test here
		assertEquals("Problematic triangle", new Vector(new Point3D(1 / x, 2 / x, 3 / x)),
				newTriangle.getNormal(new Point3D(0, 0, 0)));
	}
	
	@Test
	public void findIntersections(Ray ray) {

        Polygon triangle = new Polygon(new Point3D(1, 0, 0), new Point3D(2, 1, 0), new Point3D(3, -1, -1));
		// TC01: Ray is parallel, and included in the tri-angle.
        List<Point3D> expected = triangle.findIntersections(new Ray(new Point3D(2.28, 2.42, 0),new Vector(-0.28, -0.55, 0.48)));

        
        
        // TC01: There is a simple single test here
        // TC01: There is a simple single test here
        // TC01: There is a simple single test here
        // TC01: There is a simple single test here
        // TC01: There is a simple single test here
        // TC01: There is a simple single test here
        // TC01: There is a simple single test here
	}


}
