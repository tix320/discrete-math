package discretemath.bool.expression;

import java.util.Map;

import discretemath.bool.expression.exception.VariableValueNotSpecifiedException;
import discretemath.bool.operator.FunctionallyCompleteOperatorsSet;

public interface BooleanExpression {

	boolean evaluate(Map<BooleanVariable, Boolean> arguments) throws VariableValueNotSpecifiedException;

	BooleanExpression expressViaOperators(FunctionallyCompleteOperatorsSet operatorsSet);

	BooleanExpression normalize();
}
