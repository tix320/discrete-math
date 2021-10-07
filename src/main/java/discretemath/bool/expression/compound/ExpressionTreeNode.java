package discretemath.bool.expression.compound;

import discretemath.bool.expression.atomic.AtomicBooleanExpression;
import discretemath.bool.expression.BooleanExpression;

public sealed interface ExpressionTreeNode permits OperatorNode, ValueNode {

	int getHeight();

	static ExpressionTreeNode fromBooleanExpression(BooleanExpression expression) {
		if (expression instanceof AtomicBooleanExpression atomicBooleanExpression) {
			return new ValueNode(atomicBooleanExpression);
		} else if (expression instanceof CompoundBooleanExpression compoundBooleanExpression) {
			return compoundBooleanExpression.getRootNode();
		} else {
			throw new UnsupportedOperationException(expression.getClass().toString());
		}
	}

	static ExpressionTreeNode[] fromBooleanExpression(BooleanExpression[] expressions) {
		ExpressionTreeNode[] nodes = new ExpressionTreeNode[expressions.length];
		for (int i = 0; i < expressions.length; i++) {
			BooleanExpression expression = expressions[i];
			nodes[i] = fromBooleanExpression(expression);
		}

		return nodes;
	}

	static BooleanExpression[] toBooleanExpression(ExpressionTreeNode[] nodes) {
		BooleanExpression[] operandExpressions = new BooleanExpression[nodes.length];
		for (int i = 0; i < nodes.length; i++) {
			ExpressionTreeNode operandNode = nodes[i];

			operandExpressions[i] = toBooleanExpression(operandNode);
		}

		return operandExpressions;
	}

	static BooleanExpression toBooleanExpression(ExpressionTreeNode node) {
		if (node instanceof ValueNode valueNode) {
			return valueNode.expression();
		} else {
			return new CompoundBooleanExpression((OperatorNode) node);
		}
	}
}
