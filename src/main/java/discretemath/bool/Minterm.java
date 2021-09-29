package discretemath.bool;

import java.util.LinkedHashSet;

import discretemath.bool.expression.BooleanExpression;
import discretemath.bool.operator.FunctionallyCompleteOperatorsSet;

public interface Minterm extends BooleanExpression {

	LinkedHashSet<Literal> literals();

	@Override
	default BooleanExpression expressViaOperators(FunctionallyCompleteOperatorsSet operatorsSet) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	default BooleanExpression normalize() {
		return this;
	}
}
