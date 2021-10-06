package discretemath.bool.expression;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import discretemath.common.VariableSymbols;

public final class BooleanVariable implements AtomicBooleanExpression {

	private final char symbol;

	public BooleanVariable(char symbol) {
		this.symbol = symbol;
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

	public static List<BooleanVariable> getVariables(int variablesCount) {
		return StreamSupport.stream(VariableSymbols.symbolsFor(variablesCount).spliterator(), false)
				.map(BooleanVariable::new)
				.collect(Collectors.toList());
	}
}
