package libras.preprocessing.representation;

public class AbcissaCoordinateDimensionBuilder extends DimensionBuilder {
	
	private AbcissaCoordinateDimensionBuilder() {
		
	}
	
	private static DimensionBuilder instance = null;
	
	public static DimensionBuilder getInstance() {
		if (instance == null)
			instance = new AbcissaCoordinateDimensionBuilder();
		
		return instance;
	}
	
	@Override
	public Double[] buildDimension(Coordinate[] coordinates) {
		Double[] dimension = new Double[coordinates.length];
		
		for (int i = 0; i < dimension.length; i++) {
			if (coordinates[i] != null)
				dimension[i] = coordinates[i].getAbcissa();
		}
		
		return dimension;
	}

}
