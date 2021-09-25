package discretemath.bool.operator;

import discretemath.bool.expression.BooleanExpression;
import discretemath.bool.expression.exception.InvalidArgumentCountException;

public interface Operator {

	BooleanExpression applyTo(BooleanExpression... operands);

	BooleanExpression applyUsing(FunctionallyCompleteOperatorsSet operatorsSet,
								 BooleanExpression... operands) throws InvalidArgumentCountException, UnsupportedOperationException;

	String toString(BooleanExpression... operands) throws InvalidArgumentCountException;
}
