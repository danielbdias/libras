package libras.batches.taskfiles.annotations;

import java.lang.annotation.*;

/**
 * Represents a validation method metadata.
 * @author Daniel Baptista Dias
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidationMethod {
	/**
	 * Node name of a Validation method name in a task file.
	 * @return Name of the validation method in the task file.
	 */
	String nodeName();
}
