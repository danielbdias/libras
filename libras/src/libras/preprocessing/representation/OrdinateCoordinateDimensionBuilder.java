package libras.preprocessing.representation;

public class OrdinateCoordinateDimensionBuilder extends DimensionBuilder {

	private OrdinateCoordinateDimensionBuilder() {
		
	}
	
	private static DimensionBuilder instance = null;
	
	public static DimensionBuilder getInstance() {
		if (instance == null)
			instance = new OrdinateCoordinateDimensionBuilder();
		
		return instance;
	}
	
	@Override
	public Double[] buildDimension(Coordinate[] coordinates) {
		Double[] dimension = new Double[coordinates.length];
		
		for (int i = 0; i < dimension.length; i++) {
			if (dimension[i] != null)
				dimension[i] = coordinates[i].getOrdinate();
		}
		
		return dimension;
	}

}
