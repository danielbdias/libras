package libras.images;

import java.awt.image.DataBuffer;
import java.security.InvalidParameterException;

public enum PixelColorDepth {
	EightBits(8),
	EightBitsGrayscale(8),
	TwentyFourBits(24);
	
	private final int colorDepth;
	
	PixelColorDepth(int colorDepth) {
		this.colorDepth = colorDepth;
	}
	
	public int colorDepth() {
		return this.colorDepth;
	}
	
	public int numberOfBits() {
		return this.colorDepth / 8;
	}
	
	public static PixelColorDepth getColorDepth(int colorDepth, int bands) {
		if (colorDepth == DataBuffer.TYPE_BYTE && bands == 1)
			return PixelColorDepth.EightBitsGrayscale;
		else if (colorDepth == DataBuffer.TYPE_BYTE && bands == 3)
			return PixelColorDepth.EightBits;
		else if (colorDepth == DataBuffer.TYPE_INT && bands == 3)
			return PixelColorDepth.TwentyFourBits;
		else		
			throw new InvalidParameterException("Cannot recognize the \"colorDepth\" parameter.");
	}
}
