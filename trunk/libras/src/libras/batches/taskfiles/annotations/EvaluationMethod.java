package libras.batches.taskfiles.annotations;

import java.lang.annotation.*;

/**
 * Represents a validation method metadata.
 * @author Daniel Baptista Dias
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EvaluationMethod {
	/**
	 * Node name of a Evaluation method name in a task file.
	 * @return Name of the evaluation method in the task file.
	 */
	String nodeName();
}