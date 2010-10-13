package libras.utils;

public class RandomUtil {
	public static double randomInRange(double min, double max) {
		return ((Math.random() * (max - (min)) ) + min);
	}
}
