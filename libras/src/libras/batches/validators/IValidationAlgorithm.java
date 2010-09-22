package libras.batches.validators;

import java.util.ArrayList;

public interface IValidationAlgorithm<T> {

	public abstract void doValidation(ArrayList<T[]> data, ArrayList<String> labels) throws Exception;

}