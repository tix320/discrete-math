package discretemath.bool;

import discretemath.bool.expression.BooleanExpression;
import discretemath.bool.expression.BooleanVariable;
import discretemath.bool.operator.FunctionallyCompleteOperatorsSet;

public interface Literal extends BooleanExpression {

	BooleanVariable variable();

	boolean isPositive();

	@Override
	default BooleanExpression expressViaOperators(FunctionallyCompleteOperatorsSet operatorsSet) {
		return this;
	}

	@Override
	default BooleanExpression normalize() {
		return this;
	}
}
