/**
 * hhhh
 */
package unittests.primitives;

import static org.junit.Assert.*;
import static primitives.Util.isZero;

import org.junit.Test;

import primitives.*;

/**
 * 
 */
public class VectorTest {

	/**
	 * Test method for {@link primitives.Vector#add(primitives.Vector)}.
	 */
	@Test
	public void testAdd() {
		Vector newVector1 = new Vector(3,3,3);
		Vector newVector2 = new Vector(2,3,4);
		
		// ============ Equivalence Partitions Tests ==============		
		// Test that connection works properly
		Vector result = newVector1.add(newVector2);
		assertTrue("add() Vector did not add properly",(result.getHead().equals(new Point3D(5,6,7))));
	}

	/**
	 * Test method for {@link primitives.Vector#subtract(primitives.Vector)}.
	 */
	@Test
	public void testSubtract() {
		Vector newVector1 = new Vector(5,6,7);
		Vector newVector2 = new Vector(10,4,7);
		
		// ============ Equivalence Partitions Tests ==============		
		// Test that subtraction works properly
		Vector result = newVector1.subtract(newVector2);
		assertTrue("subtract() Vector did not subtract properly",(result.getHead().equals(new Point3D(-5,2,0))));
	}

	/**
	 * Test method for {@link primitives.Vector#scale(double)}.
	 */
	@Test
	public void testScale() {
		Vector newVector = new Vector(5,6,7);
		double scale = 11.0;
		
		// ============ Equivalence Partitions Tests ==============		
		// Test that product of Vector by a number works properly
		Vector result = newVector.scale(scale);
		assertTrue("scale() Vector has not been properly multiplied by the number",(result.getHead().equals(new Point3D(55,66,77))));
	}

	/**
	 * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
	 */
	@Test
	public void testCrossProduct() {
		Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);

        // ============ Equivalence Partitions Tests ==============
        Vector v3 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v3);

        // Test that length of cross-product is proper (orthogonal vectors taken for simplicity)
        assertEquals("crossProduct() wrong result length", v1.length() * v3.length(), vr.length(), 0.00001);

        // Test cross-product result orthogonality to its operands
        assertTrue("crossProduct() result is not orthogonal to 1st operand", isZero(vr.dotProduct(v1)));
        assertTrue("crossProduct() result is not orthogonal to 2nd operand", isZero(vr.dotProduct(v3)));

        // =============== Boundary Values Tests ==================
        // test zero vector from cross-productof co-lined vectors
        try {
            v1.crossProduct(v2);
            fail("crossProduct() for parallel vectors does not throw an exception");
        } catch (Exception e) {}

	}

	/**
	 * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
	 */
	@Test
	public void testDotProduct() {
		Vector newVector1 = new Vector(1, 2, 3);
        Vector newVector2 = new Vector(-2, -4, -6);
        Vector newVector3 = new Vector(0, 3, -2);
        
		// ============ Equivalence Partitions Tests ==============
		// Test that dotProduct of orthogonal vectors works properly
		double result1 = newVector1.dotProduct(newVector3);
		assertTrue("dotProduct() for orthogonal vectors is not zero",(isZero(result1)));
		
		// Test that dotProduct works properly
		double result2 = newVector1.dotProduct(newVector2) + 28;
		assertTrue("dotProduct() wrong value",(isZero(result2)));
	}

	/**
	 * Test method for {@link primitives.Vector#lengthSquared()}.
	 */
	@Test
	public void testLengthSquared() {
		Vector newVector = new Vector(1, 2, 3);
		
		// ============ Equivalence Partitions Tests ==============
		// Test that lengthSquared() works properly
		double result = newVector.lengthSquared() - 14;
		assertTrue("lengthSquared() wrong value",(isZero(result)));
	}

	/**
	 * Test method for {@link primitives.Vector#length()}.
	 */
	@Test
	public void testLength() {
		Vector newVector = new Vector(0, 3, 4);
		
		// ============ Equivalence Partitions Tests ==============
		// Test that length() works properly
		double result = newVector.length() - 5;
		assertTrue("length() wrong value",(isZero(result)));
	}

	/**
	 * Test method for {@link primitives.Vector#normalize()}.
	 */
	@Test
	public void testNormalize() {
        Vector v = new Vector(1, 2, 3);
        Vector vCopy = new Vector(v.getHead());
        Vector vCopyNormalize = vCopy.normalize();
        
        // ============ Equivalence Partitions Tests ==============
        // test vector normalization vs vector length and cross-product
        assertTrue("normalize() function creates a new vector",(vCopy == vCopyNormalize));
        assertTrue("normalize() result is not a unit vector",(isZero(vCopyNormalize.length() - 1)));
	}

	/**
	 * Test method for {@link primitives.Vector#normalized()}.
	 */
	@Test
	public void testNormalized() {
		Vector newVector = new Vector(1, 2, 3);
		Vector resultVector = newVector.normalized();
        
        // ============ Equivalence Partitions Tests ==============
        // test vector normalization
        assertTrue("normalizated() function does not create a new vector",(newVector != resultVector));
	}

}