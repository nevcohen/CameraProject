package unittests.primitives;

import static org.junit.Assert.*;
import static primitives.Util.isZero;

import org.junit.Test;

import primitives.*;

/**
 * Testing Vectors
 */
public class VectorTest {

	/**
	 * Test method for {@link primitives.Vector#add(primitives.Vector)}.
	 */
	@Test
	public void testAdd() {
		Vector newVector1 = new Vector(3, 3, 3);
		Vector newVector2 = new Vector(2, 3, 4);
		Vector result = newVector1.add(newVector2);
		// ============ Equivalence Partitions Tests ==============

		// TC01: Test that connection works properly
		assertEquals("add() Vector did not add properly", new Point3D(5, 6, 7), result.getHead());
	}

	/**
	 * Test method for {@link primitives.Vector#subtract(primitives.Vector)}.
	 */
	@Test
	public void testSubtract() {
		Vector newVector1 = new Vector(5, 6, 7);
		Vector newVector2 = new Vector(10, 4, 7);
		Vector result = newVector1.subtract(newVector2);
		// ============ Equivalence Partitions Tests ==============

		// TC01: Test that subtraction works properly
		assertEquals("subtract() Vector did not subtract properly", new Point3D(-5, 2, 0), result.getHead());
	}

	/**
	 * Test method for {@link primitives.Vector#scale(double)}.
	 */
	@Test
	public void testScale() {
		Vector newVector = new Vector(5, 6, 7);
		double scale = 11.0;

		// ============ Equivalence Partitions Tests ==============
		// TC01: Test that product of Vector by a number works properly
		Vector result = newVector.scale(scale);
		assertEquals("scale() Vector has not been properly multiplied by the number", new Point3D(55, 66, 77),
				result.getHead());
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

		// TC01: Test that length of cross-product is proper (orthogonal vectors taken
		// for simplicity)
		assertEquals("crossProduct() wrong result length", v1.length() * v3.length(), vr.length(), 0.00001);

		// TC02: Test cross-product result orthogonality to its operands
		assertTrue("crossProduct() result is not orthogonal to 1st operand", isZero(vr.dotProduct(v1)));
		assertTrue("crossProduct() result is not orthogonal to 2nd operand", isZero(vr.dotProduct(v3)));

		// =============== Boundary Values Tests ==================
		// TC11: test zero vector from cross-productof co-lined vectors
		try {
			v1.crossProduct(v2);
			fail("crossProduct() for parallel vectors does not throw an exception");
		} catch (Exception e) {
		}

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
		// TC01: Test that dotProduct of orthogonal vectors works properly
		double result1 = newVector1.dotProduct(newVector3);
		assertTrue("dotProduct() for orthogonal vectors is not zero", (isZero(result1)));

		// TC02: Test that dotProduct works properly
		double result2 = newVector1.dotProduct(newVector2) + 28;
		assertTrue("dotProduct() wrong value", isZero(result2));
	}

	/**
	 * Test method for {@link primitives.Vector#lengthSquared()}.
	 */
	@Test
	public void testLengthSquared() {
		Vector newVector = new Vector(1, 2, 3);
		double result = newVector.lengthSquared() - 14;
		// ============ Equivalence Partitions Tests ==============

		// TC01: Test that lengthSquared() works properly
		assertTrue("lengthSquared() wrong value", isZero(result));
	}

	/**
	 * Test method for {@link primitives.Vector#length()}.
	 */
	@Test
	public void testLength() {
		Vector newVector = new Vector(0, 3, 4);
		// ============ Equivalence Partitions Tests ==============

		// TC01: Test that length() works properly
		double result = newVector.length() - 5;
		assertTrue("length() wrong value", isZero(result));
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

		// TC01: test vector normalization vs vector length and cross-product
		// Comparing addresses - Check if a new variable is created or just another
		// pointer is created
		assertEquals("normalize() function creates a new vector", vCopy, vCopyNormalize);

		// TC02: Checks if the result is the unit vector
		assertTrue("normalize() result is not a unit vector", isZero(vCopyNormalize.length() - 1));
	}

	/**
	 * Test method for {@link primitives.Vector#normalized()}.
	 */
	@Test
	public void testNormalized() {
		Vector newVector = new Vector(1, 2, 3);
		Vector resultVector = newVector.normalized();
		// ============ Equivalence Partitions Tests ==============

		// TC01: test vector normalization
		assertNotEquals("normalizated() function does not create a new vector", newVector, resultVector);
	}

}