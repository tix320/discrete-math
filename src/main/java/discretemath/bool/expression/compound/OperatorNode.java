package discretemath.bool.expression.compound;

import java.util.Arrays;

import discretemath.bool.expression.BooleanExpression;
import discretemath.bool.operator.Operator;

import static discretemath.bool.operator.Operators.*;

public record OperatorNode(Operator operator, ExpressionTreeNode... operands) implements ExpressionTreeNode {

	public static OperatorNode forNegation(ExpressionTreeNode operand) {
		return new OperatorNode(NOT, operand);
	}

	public static OperatorNode forAND(ExpressionTreeNode... operands) {
		return new OperatorNode(AND, operands);
	}

	public static OperatorNode forOR(ExpressionTreeNode... operands) {
		return new OperatorNode(OR, operands);
	}

	public static OperatorNode forNAND(ExpressionTreeNode operand1, ExpressionTreeNode operand2) {
		return new OperatorNode(NAND, operand1, operand2);
	}

	public static OperatorNode forNOR(ExpressionTreeNode operand1, ExpressionTreeNode operand2) {
		return new OperatorNode(NOR, operand1, operand2);
	}

	@Override
	public int getHeight() {
		return 1 + Arrays.stream(operands()).mapToInt(ExpressionTreeNode::getHeight).max().orElse(0);
	}

	@Override
	public String toString() {
		Operator operator = operator();

		ExpressionTreeNode[] operands = operands();

		BooleanExpression[] operandExpressions = ExpressionTreeNode.toBooleanExpression(operands);

		return operator.toString(operandExpressions);
	}
}
