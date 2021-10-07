package discretemath.bool.expression.minterm;

import java.util.Map;

import discretemath.bool.expression.BooleanVariable;
import discretemath.bool.expression.exception.VariableValueNotSpecifiedException;

public record SimpleLiteral(BooleanVariable variable, boolean isPositive) implements Literal {

	@Override
	public boolean evaluate(Map<BooleanVariable, Boolean> arguments) throws VariableValueNotSpecifiedException {
		Boolean value = arguments.get(variable);

		if (value == null) {
			throw new VariableValueNotSpecifiedException(variable);
		}

		if (isPositive) {
			return value;
		} else {
			return !value;
		}
	}

	@Override
	public String toString() {
		return isPositive ? variable.toString() : variable + "′";
	}
}
