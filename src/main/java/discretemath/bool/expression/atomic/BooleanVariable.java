package discretemath.bool.expression.atomic;

import java.util.Map;
import java.util.Objects;

import discretemath.bool.expression.exception.VariableValueNotSpecifiedException;

public final class BooleanVariable implements AtomicBooleanExpression {

	private final char symbol;

	public BooleanVariable(char symbol) {
		this.symbol = symbol;
	}

	@Override
	public boolean evaluate(Map<BooleanVariable, Boolean> arguments) throws VariableValueNotSpecifiedException {
		Boolean value = arguments.get(this);

		if (value == null) {
			throw new VariableValueNotSpecifiedException(this);
		}

		return value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		BooleanVariable that = (BooleanVariable) o;
		return symbol == that.symbol;
	}

	@Override
	public int hashCode() {
		return Objects.hash(symbol);
	}

	@Override
	public String toString() {
		return String.valueOf(symbol);
	}
}
