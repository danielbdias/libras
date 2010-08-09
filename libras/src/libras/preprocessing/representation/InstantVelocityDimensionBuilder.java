package libras.preprocessing.representation;

public class InstantVelocityDimensionBuilder extends DimensionBuilder {
	private InstantVelocityDimensionBuilder() {
		
	}
	
	private static DimensionBuilder instance = null;
	
	public static DimensionBuilder getInstance() {
		if (instance == null)
			instance = new InstantVelocityDimensionBuilder();
		
		return instance;
	}
	
	@Override
	public Double[] buildDimension(Coordinate[] coordinates) {
		Double[] dimension = new Double[coordinates.length];
		
		if (dimension.length > 0) {
			
			dimension[0] = 0.0;
			
			for (int i = 1; i < dimension.length; i++) {
				if (coordinates[i] != null && coordinates[i-1] != null)
					dimension[i] = this.calculate(coordinates[i-1], coordinates[i]);
			}
		}
		return dimension;
	}
	
	private double calculate(Coordinate first, Coordinate second)
	{
		double[] firstAsArray = { first.getAbcissa(), first.getOrdinate() }; 
		double[] secondAsArray = { second.getAbcissa(), second.getOrdinate() };
        
		return libras.utils.MathHelper.euclideanDistance(firstAsArray, secondAsArray);
	}
}
