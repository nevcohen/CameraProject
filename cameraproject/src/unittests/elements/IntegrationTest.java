/**
 * 
 */
package unittests.elements;

import static org.junit.Assert.*;

import org.junit.Test;

import elements.Camera;
import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * Integration Test for Camera (with view plane) to intersectable shape
 */
public class IntegrationTest {

	/**
	 * Test Camera to Sphere
	 * 
	 * Test method for
	 * {@link elements.Camera#constructRayThroughPixel(int, int, int, int)}.
	 */
	@Test
	public void testSphere() {

		// TC01: Radius=1, a small Sphere in front of the view plane
		Sphere sphere = new Sphere(new Point3D(0, 0, -3), 1);
		Camera camera = new Camera(new Point3D(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0));
		camera.setViewPlaneSize(3, 3);
		camera.setViewPlaneDistance(1);

		assertEquals("sphere, radius 1, wrong number of intersection points", 2, getSumIntersections(camera, sphere));

		// TC02: Radius=2.5, large Sphere, camera behind the Sphere, view plane inside
		// the Sphere
		sphere = new Sphere(new Point3D(0, 0, -2.5), 2.5);
		camera = new Camera(new Point3D(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, 1, 0));
		camera.setViewPlaneSize(3, 3);
		camera.setViewPlaneDistance(1);

		assertEquals("sphere, radius 2.5, wrong number of intersection points", 18,
				getSumIntersections(camera, sphere));

		// TC03: Radius=2, medium Sphere, camera behind the Sphere, Sphere inside the
		// view plane
		sphere = new Sphere(new Point3D(0, 0, -2), 2);

		assertEquals("sphere, radius 2, wrong number of intersection points", 10, getSumIntersections(camera, sphere));

		// TC04: Radius=4, large Sphere, camera and plane inside the Sphere
		sphere = new Sphere(new Point3D(0, 0, -2), 4);

		assertEquals("sphere, radius 4, wrong number of intersection points", 9, getSumIntersections(camera, sphere));

		// TC05: Radius=0.5, a small Sphere behind the camera
		sphere = new Sphere(new Point3D(0, 0, 1), 0.5);

		assertEquals("sphere, radius 0.5, wrong number of intersection points", 0, getSumIntersections(camera, sphere));
	}

	/**
	 * Test Camera to Plane
	 * 
	 * Test method for
	 * {@link elements.Camera#constructRayThroughPixel(int, int, int, int)}.
	 */
	@Test
	public void testPlane() {
		Camera camera = new Camera(new Point3D(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0));
		camera.setViewPlaneSize(3, 3);
		camera.setViewPlaneDistance(1);
		
		// TC01: Plane is parallel to the view plane
 		Plane plane = new Plane(new Point3D(2, 0, -1), new Vector(0,0,-1));
 		assertEquals("plane, paralell to the view plane, wrong number of intersection points", 9, getSumIntersections(camera, plane));
 		
    	// TC02: Plane has angles that allow for 9 points
		plane = new Plane(new Point3D(0, 0, -3), new Vector(0, 0.5, -1));
		assertEquals("plane, paralell to the view plane, wrong number of intersection points", 9,
		getSumIntersections(camera, plane));
 		
    	// TC03: Plane has a 90 degree angle with ray
		plane = new Plane(new Point3D(0, 0, -3), new Vector(0, 1, -1));
		assertEquals("plane, paralell to the view plane, wrong number of intersection points", 6,
				getSumIntersections(camera, plane));
	}

	/**
	 * Test Camera to Triangle
	 * 
	 * Test method for
	 * {@link elements.Camera#constructRayThroughPixel(int, int, int, int)}.
	 */
	@Test
	public void testTriangle() {
		Camera camera = new Camera(new Point3D(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0));
		camera.setViewPlaneSize(3, 3);
		camera.setViewPlaneDistance(1);
		
		// TC01: small triangle is in range of 1 vector
		Triangle triangle = new Triangle(new Point3D(0, 1, -2), new Point3D(1, -1, -2), new Point3D(-1, -1, -2));
		assertEquals("triangle, is small, creating one point, wrong number of intersection points", 1,
				getSumIntersections(camera, triangle));

		// TC02: triangle is large enough to create two intersection points
		triangle = new Triangle(new Point3D(0, 20, -2), new Point3D(1, -1, -2), new Point3D(-1, -1, -2));
		assertEquals("triangle, is small, creating one point, wrong number of intersection points", 2,
				getSumIntersections(camera, triangle));
	}

	/**
	 * Creating rays from the Camera to the view plane and counting the amount of
	 * intersection points on the Rays
	 * 
	 * @param camera The camera from which the rays are sent
	 * @param shape  intersected shape
	 * @return amount of intersection points
	 */
	private int getSumIntersections(Camera camera, Intersectable shape) {
		int sum = 0;
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				Ray ray = camera.constructRayThroughPixel(3, 3, i, j);
				if (shape.findIntersections(ray) != null)
					sum += shape.findIntersections(ray).size();
			}
		return sum;
	}

}
