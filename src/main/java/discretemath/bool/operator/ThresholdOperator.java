package discretemath.bool.operator;

import discretemath.bool.expression.BooleanExpression;
import discretemath.bool.expression.compound.CompoundBooleanExpression;
import discretemath.bool.expression.compound.ExpressionTreeNode;
import discretemath.bool.expression.compound.OperatorNode;
import discretemath.bool.expression.exception.InvalidArgumentCountException;

public class ThresholdOperator implements Operator {

	private final double threshold;

	private final double[] weights;

	public ThresholdOperator(double threshold, double[] weights) {
		this.threshold = threshold;
		this.weights = weights;
	}

	@Override
	public boolean evaluate(boolean... values) {
		InvalidArgumentCountException.checkExact(weights.length, values.length);

		double sum = 0;

		for (int i = 0; i < weights.length; i++) {
			double weight = weights[i];

			if (values[i]) {
				sum += weight;
			}
		}

		return sum >= threshold;
	}

	@Override
	public BooleanExpression applyTo(BooleanExpression... operands) {
		InvalidArgumentCountException.checkExact(weights.length, operands.length);

		ExpressionTreeNode[] operandNodes = ExpressionTreeNode.fromBooleanExpression(operands);

		return new CompoundBooleanExpression(new OperatorNode(this, operandNodes));
	}

	@Override
	public BooleanExpression applyUsing(FunctionallyCompleteOperatorsSet operatorsSet,
										BooleanExpression... operands) throws InvalidArgumentCountException, UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString(BooleanExpression... operands) throws InvalidArgumentCountException {
		InvalidArgumentCountException.checkExact(weights.length, operands.length);


		return OperatorsStringUtils.buildNAryOperatorString(" 〶 ", operands);
	}
}
