package discretemath.bool.expression.compound;

import java.util.Map;
import java.util.Objects;

import discretemath.bool.expression.atomic.AtomicBooleanExpression;
import discretemath.bool.expression.BooleanExpression;
import discretemath.bool.expression.atomic.BooleanVariable;
import discretemath.bool.expression.exception.VariableValueNotSpecifiedException;
import discretemath.bool.operator.FunctionallyCompleteOperatorsSet;
import discretemath.bool.operator.Operator;
import discretemath.bool.operator.Operators;

public final class CompoundBooleanExpression implements BooleanExpression {

	private final OperatorNode rootNode;

	public CompoundBooleanExpression(OperatorNode rootNode) {
		this.rootNode = Objects.requireNonNull(rootNode, "Root not may not be null");
	}

	public OperatorNode getRootNode() {
		return rootNode;
	}

	@Override
	public boolean evaluate(Map<BooleanVariable, Boolean> arguments) throws VariableValueNotSpecifiedException {
		return evaluateNode(rootNode, arguments);
	}

	@Override
	public BooleanExpression expressViaOperators(FunctionallyCompleteOperatorsSet operatorsSet) {
		return expressExpressionViaOperators(this.rootNode, operatorsSet);
	}

	@Override
	public BooleanExpression normalize() {
		OperatorNode newRootNode = (OperatorNode) deleteDoubleNegations(rootNode, false);
		return new CompoundBooleanExpression(newRootNode);
	}

	@Override
	public String toString() {
		return rootNode.toString();
	}

	private static boolean evaluateNode(ExpressionTreeNode node, Map<BooleanVariable, Boolean> values) {
		if (node instanceof ValueNode valueNode) {
			AtomicBooleanExpression expression = valueNode.expression();
			return expression.evaluate(values);
		} else {
			OperatorNode operatorNode = (OperatorNode) node;
			Operator operator = operatorNode.operator();
			ExpressionTreeNode[] operands = operatorNode.operands();
			boolean[] operandsValues = new boolean[operands.length];
			for (int i = 0; i < operands.length; i++) {
				ExpressionTreeNode operand = operands[i];

				operandsValues[i] = evaluateNode(operand, values);
			}

			return operator.evaluate(operandsValues);
		}
	}

	private static BooleanExpression expressExpressionViaOperators(ExpressionTreeNode node,
																   FunctionallyCompleteOperatorsSet operatorsSet) {
		if (node instanceof ValueNode valueNode) {
			return valueNode.expression();
		}

		OperatorNode operatorNode = (OperatorNode) node;
		Operator operator = operatorNode.operator();
		ExpressionTreeNode[] operands = operatorNode.operands();

		BooleanExpression[] operandExpressions = ExpressionTreeNode.toBooleanExpression(operands);

		return operator.applyUsing(operatorsSet, operandExpressions);
	}

	private static ExpressionTreeNode deleteDoubleNegations(ExpressionTreeNode node, boolean negationAlreadyFound) {
		if (node instanceof ValueNode) {
			if (negationAlreadyFound) {
				return OperatorNode.forNegation(node);
			} else {
				return node;
			}
		}

		OperatorNode operatorNode = (OperatorNode) node;

		if (operatorNode.operator() == Operators.NOT) {
			ExpressionTreeNode operand = operatorNode.operands()[0];

			if (negationAlreadyFound) {
				return deleteDoubleNegations(operand, false);
			} else {
				return deleteDoubleNegations(operand, true);
			}
		} else {
			ExpressionTreeNode[] operands = operatorNode.operands();
			ExpressionTreeNode[] newOperands = new ExpressionTreeNode[operands.length];
			for (int i = 0; i < operands.length; i++) {
				ExpressionTreeNode operand = operands[i];
				newOperands[i] = deleteDoubleNegations(operand, false);
			}

			OperatorNode newOperatorNode = new OperatorNode(operatorNode.operator(), newOperands);
			if (negationAlreadyFound) {
				return OperatorNode.forNegation(newOperatorNode);
			} else {
				return newOperatorNode;
			}
		}
	}
}

