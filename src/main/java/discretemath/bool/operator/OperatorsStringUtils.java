package discretemath.bool.operator;

import java.util.StringJoiner;

import discretemath.bool.expression.BooleanExpression;
import discretemath.bool.expression.atomic.AtomicBooleanExpression;
import discretemath.bool.expression.compound.CompoundBooleanExpression;

import static discretemath.bool.operator.Operators.NOT;

public class OperatorsStringUtils {

	public static String buildNAryOperatorString(String delimiter, BooleanExpression... operands) {
		StringJoiner joiner = new StringJoiner(delimiter);
		for (BooleanExpression operand : operands) {
			joiner.add(wrapWithParentheses(operand));
		}

		return joiner.toString();
	}

	public static String wrapWithParentheses(BooleanExpression expression) {
		if (expression instanceof AtomicBooleanExpression) {
			return expression.toString();
		} else {
			if (expression instanceof CompoundBooleanExpression compoundBooleanExpression) {
				if (compoundBooleanExpression.getRootNode().operator() == NOT) {
					return expression.toString();
				}
			}

			return "(" + expression.toString() + ")";
		}
	}
}
