package discretemath.bool.expression.forms;

import java.util.List;

import discretemath.bool.expression.BooleanExpression;
import discretemath.bool.operator.FunctionallyCompleteOperatorsSet;

public interface Maxterm extends BooleanExpression {

	List<Literal> literals();

	@Override
	default BooleanExpression expressViaOperators(FunctionallyCompleteOperatorsSet operatorsSet) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	default BooleanExpression normalize() {
		return this;
	}
}

