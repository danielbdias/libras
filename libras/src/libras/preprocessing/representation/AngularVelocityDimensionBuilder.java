package libras.preprocessing.representation;

public class AngularVelocityDimensionBuilder extends DimensionBuilder {

	private AngularVelocityDimensionBuilder() {
		
	}
	
	private static DimensionBuilder instance = null;
	
	public static DimensionBuilder getInstance() {
		if (instance == null)
			instance = new AngularVelocityDimensionBuilder();
		
		return instance;
	}
	
	@Override
	public Double[] buildDimension(Coordinate[] coordinates) {
		Double[] dimension = AbcissaAngleDimensionBuilder.getInstance().buildDimension(coordinates);
		
		if (dimension.length > 0) {
			dimension[0] = 0.0;
			
			for (int i = 1; i < dimension.length; i++) {
				if (dimension[i] != null && dimension[i-1] != null)
					dimension[i] = dimension[i] - dimension[i-1];
			}
		}
		
		return dimension;
	}
}
