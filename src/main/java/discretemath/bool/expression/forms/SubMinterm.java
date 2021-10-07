package discretemath.bool.expression.forms;

import java.util.List;
import java.util.Map;

import discretemath.bool.expression.atomic.BooleanVariable;
import discretemath.bool.expression.exception.VariableValueNotSpecifiedException;
import discretemath.bool.operator.Operators;

public final class SubMinterm implements Minterm {

	private final List<Literal> subList;

	public SubMinterm(Minterm minterm, int fromIndex, int toIndex) {
		this.subList = minterm.literals().subList(fromIndex, toIndex);
	}

	@Override
	public List<Literal> literals() {
		return subList;
	}

	@Override
	public boolean evaluate(Map<BooleanVariable, Boolean> arguments) throws VariableValueNotSpecifiedException {
		boolean[] values = new boolean[subList.size()];
		for (int i = 0; i < subList.size(); i++) {
			Literal literal = subList.get(i);

			values[i] = literal.evaluate(arguments);
		}

		return Operators.AND.evaluate(values);
	}
}
