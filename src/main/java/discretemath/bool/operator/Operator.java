package discretemath.bool.operator;

import discretemath.bool.expression.BooleanExpression;
import discretemath.bool.expression.exception.InvalidArgumentCountException;

public interface Operator {

	boolean evaluate(boolean... values);

	BooleanExpression applyTo(BooleanExpression... operands) throws InvalidArgumentCountException;

	BooleanExpression applyUsing(FunctionallyCompleteOperatorsSet operatorsSet,
								 BooleanExpression... operands) throws InvalidArgumentCountException, UnsupportedOperationException;

	String toString(BooleanExpression... operands) throws InvalidArgumentCountException;
}
