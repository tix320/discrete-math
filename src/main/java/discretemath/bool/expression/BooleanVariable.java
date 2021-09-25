package discretemath.bool.expression;

import java.util.Objects;

public final class BooleanVariable implements AtomicBooleanExpression {

	private final char symbol;

	public BooleanVariable(char symbol) {
		this.symbol = symbol;
	}

	@Override
	public String toString() {
		return String.valueOf(symbol);
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
}
