package discretemath.bool.expression;

import discretemath.bool.operator.FunctionallyCompleteOperatorsSet;

public interface BooleanExpression {

	BooleanExpression expressViaOperators(FunctionallyCompleteOperatorsSet operatorsSet);

	BooleanExpression normalize();
}
