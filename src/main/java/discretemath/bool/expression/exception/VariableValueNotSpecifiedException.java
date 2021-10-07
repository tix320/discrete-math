package discretemath.bool.expression.exception;

import discretemath.bool.expression.atomic.BooleanVariable;

public class VariableValueNotSpecifiedException extends RuntimeException {

	public VariableValueNotSpecifiedException(BooleanVariable variable) {
		super("Variable[%s]".formatted(variable));
	}
}
