/**
 * Tests for libras.utils package
 */
package libras.utils.tests;

import static org.junit.Assert.*;

import java.security.InvalidParameterException;
import libras.utils.MathHelper;
import org.junit.Test;

/**
 * Provides tests for {@link libras.utils.MathHelper}.
 * @author Daniel Dias
 */
public class MathHelperTest
{
	/**
	 * Test method for {@link libras.utils.MathHelper#euclideanDistance(int[], int[])}.
	 */
	@Test
	public void testEuclideanDistanceIntArrayIntArray()
	{
		int result = Integer.MIN_VALUE; 
		
		try
		{
			result = MathHelper.euclideanDistance((int[]) null, null);
			result = MathHelper.euclideanDistance(null, new int[] {});
			result = MathHelper.euclideanDistance(new int[] {}, null);
			fail("The method accept null parameters.");
		}
		catch (InvalidParameterException e) { }
		
		try
		{
			result = MathHelper.euclideanDistance(new int[] { 0 }, new int[] {});
			fail("The method accept vectors with different lengths.");
		}
		catch (InvalidParameterException e) { }
		
		result = MathHelper.euclideanDistance(new int[] { 0 }, new int[] { 1 });
		assertEquals(1, result);
		
		result = MathHelper.euclideanDistance(new int[] { 1 }, new int[] { 2 });
		assertEquals(1, result);
		
		result = MathHelper.euclideanDistance(new int[] { 0, 1 }, new int[] { 0, 2 });
		assertEquals(1, result);
	}

	/**
	 * Test method for {@link libras.utils.MathHelper#euclideanDistance(double[], double[])}.
	 */
	@Test
	public void testEuclideanDistanceDoubleArrayDoubleArray()
	{
		double result = Double.NaN; 
		
		try
		{
			result = MathHelper.euclideanDistance((double[]) null, null);
			result = MathHelper.euclideanDistance(null, new double[] {});
			result = MathHelper.euclideanDistance(new double[] {}, null);
			fail("The method accept null parameters.");
		}
		catch (InvalidParameterException e) { }
		
		try
		{
			result = MathHelper.euclideanDistance(new double[] { 0.0 }, new double[] {});
			fail("The method accept vectors with different lengths.");
		}
		catch (InvalidParameterException e) { }
		
		result = MathHelper.euclideanDistance(new double[] { 0.0 }, new double[] { 1.0 });
		assertEquals(1.0, result, 0.0);
		
		result = MathHelper.euclideanDistance(new double[] { 1.0 }, new double[] { 2.0 });
		assertEquals(1.0, result, 0.0);
		
		result = MathHelper.euclideanDistance(new double[] { 0.0, 1.0 }, new double[] { 0.0, 2.0 });
		assertEquals(1.0, result, 0.0);
	}

	/**
	 * Test method for {@link libras.utils.MathHelper#sumVectors(double[], double[])}.
	 */
	@Test
	public void testSumVectors()
	{
		double[] result = null; 
		double[] expected = null;
		
		try
		{
			result = MathHelper.sumVectors((double[]) null, null);
			result = MathHelper.sumVectors(null, new double[] {});
			result = MathHelper.sumVectors(new double[] {}, null);
			fail("The method accept null parameters.");
		}
		catch (InvalidParameterException e) { }
		
		try
		{
			result = MathHelper.sumVectors(new double[] { 0.0 }, new double[] {});
			fail("The method accept vectors with different lengths.");
		}
		catch (InvalidParameterException e) { }
		
		result = MathHelper.sumVectors(new double[] { 0.0 }, new double[] { 1.0 });
		expected = new double[] { 1.0 };
		
		for (int i = 0; i < result.length; i++)
			assertEquals(
				String.format("The %d st item isn't equal. Expected: %f  Actual: %f", i+1, expected[i], result[i]),
				expected[i], result[i], 0.0);
		
		result = MathHelper.sumVectors(new double[] { 1.0 }, new double[] { 2.0 });
		expected = new double[] { 3.0 };
		
		for (int i = 0; i < result.length; i++)
			assertEquals(
				String.format("The %d st item isn't equal. Expected: %f  Actual: %f", i+1, expected[i], result[i]),
				expected[i], result[i], 0.0);
		
		result = MathHelper.sumVectors(new double[] { 0.0, 1.0 }, new double[] { 0.0, 2.0 });
		expected = new double[] { 0.0, 3.0 };
		
		for (int i = 0; i < result.length; i++)
			assertEquals(
				String.format("The %d st item isn't equal. Expected: %f  Actual: %f", i+1, expected[i], result[i]),
				expected[i], result[i], 0.0);
	}

	/**
	 * Test method for {@link libras.utils.MathHelper#subtractVectors(double[], double[])}.
	 */
	@Test
	public void testSubtractVectors()
	{
		double[] result = null; 
		double[] expected = null;
		
		try
		{
			result = MathHelper.subtractVectors((double[]) null, null);
			result = MathHelper.subtractVectors(null, new double[] {});
			result = MathHelper.subtractVectors(new double[] {}, null);
			fail("The method accept null parameters.");
		}
		catch (InvalidParameterException e) { }
		
		try
		{
			result = MathHelper.subtractVectors(new double[] { 0.0 }, new double[] {});
			fail("The method accept vectors with different lengths.");
		}
		catch (InvalidParameterException e) { }
		
		result = MathHelper.subtractVectors(new double[] { 0.0 }, new double[] { 1.0 });
		expected = new double[] { -1.0 };
		
		for (int i = 0; i < result.length; i++)
			assertEquals(
				String.format("The %d st item isn't equal. Expected: %f  Actual: %f", i+1, expected[i], result[i]),
				expected[i], result[i], 0.0);
		
		result = MathHelper.subtractVectors(new double[] { 1.0 }, new double[] { 2.0 });
		for (int i = 0; i < result.length; i++)
			assertEquals(
				String.format("The %d st item isn't equal. Expected: %f  Actual: %f", i+1, expected[i], result[i]),
				expected[i], result[i], 0.0);
		
		result = MathHelper.subtractVectors(new double[] { 0.0, 1.0 }, new double[] { 0.0, 2.0 });
		expected = new double[] { 0.0, -1.0 };
		
		for (int i = 0; i < result.length; i++)
			assertEquals(
				String.format("The %d st item isn't equal. Expected: %f  Actual: %f", i+1, expected[i], result[i]),
				expected[i], result[i], 0.0);
	}

	/**
	 * Test method for {@link libras.utils.MathHelper#scalarMultiplication(double[], double)}.
	 */
	@Test
	public void testScalarMultiplication()
	{
		double[] result = null; 
		double[] expected = null;
		
		try
		{
			result = MathHelper.scalarMultiplication(null, 0.0);
			fail("The method accept null parameters.");
		}
		catch (InvalidParameterException e) { }
		
		result = MathHelper.scalarMultiplication(new double[] { 1.0 }, -1.0);
		expected = new double[] { -1.0 };
		
		for (int i = 0; i < result.length; i++)
			assertEquals(
				String.format("The %d st item isn't equal. Expected: %f  Actual: %f", i+1, expected[i], result[i]),
				expected[i], result[i], 0.0);
		
		result = MathHelper.scalarMultiplication(new double[] { 1.0 }, 0.0);
		expected = new double[] { 0.0 };
		
		for (int i = 0; i < result.length; i++)
			assertEquals(
				String.format("The %d st item isn't equal. Expected: %f  Actual: %f", i+1, expected[i], result[i]),
				expected[i], result[i], 0.0);
		
		result = MathHelper.scalarMultiplication(new double[] { 1.0, 11.0 }, 5.0);
		expected = new double[] { 5.0, 55.0 };
		
		for (int i = 0; i < result.length; i++)
			assertEquals(
				String.format("The %d st item isn't equal. Expected: %f  Actual: %f", i+1, expected[i], result[i]),
				expected[i], result[i], 0.0);
	}

}
