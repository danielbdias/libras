package libras.preprocessing.representation;

public final class AbcissaAngleDimensionBuilder extends DimensionBuilder {
	private AbcissaAngleDimensionBuilder() {
		
	}
	
	private static DimensionBuilder instance = null;
	
	public static DimensionBuilder getInstance() {
		if (instance == null)
			instance = new AbcissaAngleDimensionBuilder();
		
		return instance;
	}
	
	@Override
	public Double[] buildDimension(Coordinate[] coordinates) {
		Double[] dimension = new Double[coordinates.length];
		
		if (dimension.length > 0) {
			
			dimension[0] = 0.0;
			
			for (int i = 1; i < dimension.length; i++) {
				dimension[i] = this.calculate(coordinates[i-1], coordinates[i]);
			}
		}
		return dimension;
	}
	
	private Double calculate(Coordinate first, Coordinate second)
	{
		if (first != null && second != null) {
			double ordinateDelta = first.getOrdinate() - second.getOrdinate();
			double abscissaDelta = first.getAbcissa() - second.getAbcissa();
			double angularCoefficient = (abscissaDelta != 0 ? ordinateDelta / abscissaDelta : 0.0);
			return Math.atan(angularCoefficient);
		}
		else
		{
			return null;
		}
	}
}
