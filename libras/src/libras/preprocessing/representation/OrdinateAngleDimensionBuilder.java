package libras.preprocessing.representation;

public class OrdinateAngleDimensionBuilder extends DimensionBuilder {
	private OrdinateAngleDimensionBuilder() {
		
	}
	
	private static DimensionBuilder instance = null;
	
	public static DimensionBuilder getInstance() {
		if (instance == null)
			instance = new OrdinateAngleDimensionBuilder();
		
		return instance;
	}
	
	@Override
	public Double[] buildDimension(Coordinate[] coordinates) {
		Double[] dimension = AbcissaAngleDimensionBuilder.getInstance().buildDimension(coordinates);
		
		for (int i = 0; i < dimension.length; i++) {
			if (dimension[i] != null)
				dimension[i] = dimension[i] - 0.5;
		}
		
		return dimension;
	}
}
