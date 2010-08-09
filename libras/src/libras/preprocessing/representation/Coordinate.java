package libras.preprocessing.representation;

public class Coordinate {
	public Coordinate() {
		this(0.0, 0.0);
	}
	
	public Coordinate(double abcissa, double ordinate) {
		this.abcissa = abcissa;
		this.ordinate = ordinate;
	}
	
	private double abcissa = 0.0;
	
	private double ordinate = 0.0;
	
	public double getAbcissa() {
		return abcissa;
	}

	public double getOrdinate() {
		return ordinate;
	}
}
