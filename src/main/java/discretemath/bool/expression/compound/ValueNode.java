package discretemath.bool.expression.compound;

import discretemath.bool.expression.atomic.AtomicBooleanExpression;

public record ValueNode(AtomicBooleanExpression expression) implements ExpressionTreeNode {

	@Override
	public int getHeight() {
		return 0;
	}

	@Override
	public String toString() {
		return expression.toString();
	}
}