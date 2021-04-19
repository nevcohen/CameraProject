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
		// ============ Equivalence Partitions Tests ==============

		// TC01: There is a simple single test here
		assertEquals("getNormal() Bad normal to Triangle", new Vector(new Point3D(1 / x, 2 / x, 3 / x)),
				newTriangle.getNormal(new Point3D(4, 1d / 4, 1d / 2)));
	}
	
	@Test
    public void testFindIntersections() {

        Triangle t=new Triangle(new Point3D(2,1,0), new Point3D(0, 0, 0), new Point3D(0, 2, 0));
        List<Point3D> result;
        // EP01: ray goes inside the triangle
        result=t.findIntersections(new Ray(new Point3D(-1, -1, -1), new Vector(2, 2, 1)));
        assertEquals("Wrong point recieved",List.of(new Point3D(1,1,0)), result);

        // EP02: Outside against edge
        result=t.findIntersections(new Ray(new Point3D(-1, -2, -1), new Vector(3, 4, 1)));
        assertNull("Ray doesnt work when it touches the edge",result);

        //EP03: Outside against vertex
        result=t.findIntersections(new Ray(new Point3D(-2, -2, -1), new Vector(5, 4, 1)));
        assertNull("Ray doesnt work when it touches the vertex",result);


        // BVA01: Ray touches the corner of the triangle
        result=t.findIntersections(new Ray(new Point3D(-1, -1, -1), new Vector(1, 2, 1)));
        assertNull("Ray is outside against edge",result);

        // BVA02: Ray intersects with the vertex
        result=t.findIntersections(new Ray(new Point3D(-1, -2, -1), new Vector(1, 4, 1)));
        assertNull("Ray is outside against edge",result);

        //BVA03: Ray intersects with the continuation of the vertex
        result=t.findIntersections(new Ray(new Point3D(-3, -3, -1), new Vector(1, 6, 1)));
        assertNull("Ray is outside against edge",result);

    }


}
