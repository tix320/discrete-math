package discretemath.bool.expression.atomic;

import discretemath.bool.expression.BooleanExpression;
import discretemath.bool.operator.FunctionallyCompleteOperatorsSet;

public sealed interface AtomicBooleanExpression extends BooleanExpression
		permits BooleanVariable, ConstantBooleanValue {

	@Override
	default BooleanExpression expressViaOperators(FunctionallyCompleteOperatorsSet operatorsSet) {
		return this;
	}

	@Override
	default BooleanExpression normalize() {
		return this;
	}
}
