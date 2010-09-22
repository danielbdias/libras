package libras.utils;

public class ConverterHelper {
	public static double[] unboxArray(Double[] array) {
		double[] unboxedArray = new double[array.length];
		
		for (int j = 0; j < unboxedArray.length; j++)
			unboxedArray[j] = array[j];
		
		return unboxedArray;
	}
	
	public static Double[] boxArray(double[] array) {
		Double[] boxedArray = new Double[array.length];
		
		for (int j = 0; j < boxedArray.length; j++)
			boxedArray[j] = array[j];
		
		return boxedArray;
	}
}
