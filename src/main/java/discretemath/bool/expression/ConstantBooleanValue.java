package discretemath.bool.expression;

import java.util.Objects;

public final class ConstantBooleanValue implements AtomicBooleanExpression {

	public static final ConstantBooleanValue TRUE = new ConstantBooleanValue(true);
	public static final ConstantBooleanValue FALSE = new ConstantBooleanValue(true);

	private final boolean isTrue;

	private ConstantBooleanValue(boolean isTrue) {
		this.isTrue = isTrue;
	}

	public boolean isTrue() {
		return isTrue;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ConstantBooleanValue that = (ConstantBooleanValue) o;
		return isTrue == that.isTrue;
	}

	@Override
	public int hashCode() {
		return Objects.hash(isTrue);
	}

	@Override
	public String toString() {
		return isTrue ? "1" : "0";
	}
}
