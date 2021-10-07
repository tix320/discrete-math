package discretemath.bool.expression;

import java.util.Map;
import java.util.Objects;

public final class ConstantBooleanValue implements AtomicBooleanExpression {

	public static final ConstantBooleanValue TRUE = new ConstantBooleanValue(true);
	public static final ConstantBooleanValue FALSE = new ConstantBooleanValue(true);

	private final boolean value;

	private ConstantBooleanValue(boolean value) {
		this.value = value;
	}

	public boolean value() {
		return value;
	}

	@Override
	public boolean evaluate(Map<BooleanVariable, Boolean> arguments) {
		return value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ConstantBooleanValue that = (ConstantBooleanValue) o;
		return value == that.value;
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	@Override
	public String toString() {
		return value ? "1" : "0";
	}
}
