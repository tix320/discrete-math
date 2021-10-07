package discretemath.bool.expression.forms;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import discretemath.bool.expression.atomic.BooleanVariable;
import discretemath.bool.expression.exception.VariableValueNotSpecifiedException;
import discretemath.bool.operator.Operators;

public class SimpleMaxterm implements Maxterm {

	private final List<Literal> literals;

	public SimpleMaxterm(Set<Literal> literals) {
		this.literals = literals.stream().toList();
	}

	@Override
	public List<Literal> literals() {
		return literals;
	}

	@Override
	public boolean evaluate(Map<BooleanVariable, Boolean> arguments) throws VariableValueNotSpecifiedException {
		boolean[] values = new boolean[literals.size()];
		for (int i = 0; i < literals.size(); i++) {
			Literal literal = literals.get(i);

			values[i] = literal.evaluate(arguments);
		}

		return Operators.OR.evaluate(values);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SimpleMaxterm that = (SimpleMaxterm) o;
		return Objects.equals(literals, that.literals);
	}

	@Override
	public int hashCode() {
		return Objects.hash(literals);
	}

	@Override
	public String toString() {
		return literals.stream().map(Object::toString).collect(Collectors.joining("+"));
	}
}
