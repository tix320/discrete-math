package discretemath.bool.operator;

import java.util.StringJoiner;

import discretemath.bool.expression.AtomicBooleanExpression;
import discretemath.bool.expression.BooleanExpression;
import discretemath.bool.expression.compound.CompoundBooleanExpression;
import discretemath.bool.expression.compound.ExpressionTreeNode;
import discretemath.bool.expression.compound.OperatorNode;
import discretemath.bool.expression.compound.ValueNode;
import discretemath.bool.expression.exception.InvalidArgumentCountException;

public enum Operators implements Operator {

	AND {
		@Override
		public BooleanExpression applyTo(BooleanExpression... operands) {
			InvalidArgumentCountException.checkGTE(2, operands.length);

			ExpressionTreeNode[] operandNodes = ExpressionTreeNode.fromBooleanExpression(operands);

			return new CompoundBooleanExpression(OperatorNode.forAND(operandNodes));
		}

		@Override
		public BooleanExpression applyUsing(FunctionallyCompleteOperatorsSet operatorsSet,
											BooleanExpression... operands) throws InvalidArgumentCountException, UnsupportedOperationException {
			InvalidArgumentCountException.checkExact(2, operands.length);

			BooleanExpression[] expressedExpressions = Operators.expressViaOperators(operatorsSet, operands);
			ExpressionTreeNode[] operandNodes = ExpressionTreeNode.fromBooleanExpression(expressedExpressions);

			ExpressionTreeNode firstExpressionRootNode = operandNodes[0];
			ExpressionTreeNode secondExpressionRootNode = operandNodes[1];

			return switch (operatorsSet) {
				case OR_NOT -> new CompoundBooleanExpression(OperatorNode.forNegation(
						OperatorNode.forOR(OperatorNode.forNegation(firstExpressionRootNode),
								OperatorNode.forNegation(secondExpressionRootNode))));
				case SINGLE_NAND -> new CompoundBooleanExpression(
						OperatorNode.forNAND(OperatorNode.forNAND(firstExpressionRootNode, secondExpressionRootNode),
								OperatorNode.forNAND(firstExpressionRootNode, secondExpressionRootNode)));
				case SINGLE_NOR -> new CompoundBooleanExpression(
						OperatorNode.forNOR(OperatorNode.forNOR(firstExpressionRootNode, firstExpressionRootNode),
								OperatorNode.forNOR(secondExpressionRootNode, secondExpressionRootNode)));
				default -> throw new UnsupportedOperationException();
			};
		}

		@Override
		public String toString(BooleanExpression... operands) throws InvalidArgumentCountException {
			InvalidArgumentCountException.checkGTE(2, operands.length);

			return buildNAryOperatorString("", operands);
		}

	}, OR {
		@Override
		public BooleanExpression applyTo(BooleanExpression... operands) {
			InvalidArgumentCountException.checkGTE(2, operands.length);

			ExpressionTreeNode[] operandNodes = ExpressionTreeNode.fromBooleanExpression(operands);

			return new CompoundBooleanExpression(OperatorNode.forOR(operandNodes));
		}

		@Override
		public BooleanExpression applyUsing(FunctionallyCompleteOperatorsSet operatorsSet,
											BooleanExpression... operands) throws InvalidArgumentCountException, UnsupportedOperationException {
			InvalidArgumentCountException.checkExact(2, operands.length);

			BooleanExpression[] expressedExpressions = Operators.expressViaOperators(operatorsSet, operands);
			ExpressionTreeNode[] operandNodes = ExpressionTreeNode.fromBooleanExpression(expressedExpressions);

			ExpressionTreeNode firstExpressionRootNode = operandNodes[0];
			ExpressionTreeNode secondExpressionRootNode = operandNodes[1];

			return switch (operatorsSet) {
				case AND_NOT -> new CompoundBooleanExpression(OperatorNode.forNegation(
						OperatorNode.forAND(OperatorNode.forNegation(firstExpressionRootNode),
								OperatorNode.forNegation(secondExpressionRootNode))));
				case SINGLE_NAND -> new CompoundBooleanExpression(
						OperatorNode.forNAND(OperatorNode.forNAND(firstExpressionRootNode, firstExpressionRootNode),
								OperatorNode.forNAND(secondExpressionRootNode, secondExpressionRootNode)));
				case SINGLE_NOR -> new CompoundBooleanExpression(
						OperatorNode.forNOR(OperatorNode.forNOR(firstExpressionRootNode, secondExpressionRootNode),
								OperatorNode.forNOR(firstExpressionRootNode, secondExpressionRootNode)));
				default -> throw new UnsupportedOperationException();
			};
		}

		@Override
		public String toString(BooleanExpression... operands) throws InvalidArgumentCountException {
			InvalidArgumentCountException.checkGTE(2, operands.length);

			return buildNAryOperatorString(" + ", operands);
		}

	}, NOT {
		@Override
		public BooleanExpression applyTo(BooleanExpression... operands) {
			InvalidArgumentCountException.checkExact(1, operands.length);

			BooleanExpression operand = operands[0];

			ExpressionTreeNode treeNode;
			if (operand instanceof AtomicBooleanExpression expression) {
				treeNode = new ValueNode(expression);
			} else if (operand instanceof CompoundBooleanExpression expression) {
				treeNode = expression.getRootNode();
			} else {
				throw new UnsupportedOperationException();
			}

			return new CompoundBooleanExpression(OperatorNode.forNegation(treeNode));
		}

		@Override
		public BooleanExpression applyUsing(FunctionallyCompleteOperatorsSet operatorsSet,
											BooleanExpression... operands) throws InvalidArgumentCountException, UnsupportedOperationException {
			InvalidArgumentCountException.checkExact(1, operands.length);

			BooleanExpression booleanExpression = operands[0].expressViaOperators(operatorsSet);
			ExpressionTreeNode treeNode = ExpressionTreeNode.fromBooleanExpression(booleanExpression);

			if (operatorsSet.contains(this)) {
				return applyTo(booleanExpression);
			} else {
				return switch (operatorsSet) {
					case SINGLE_NAND -> new CompoundBooleanExpression(OperatorNode.forNAND(treeNode, treeNode));
					case SINGLE_NOR -> new CompoundBooleanExpression(OperatorNode.forNOR(treeNode, treeNode));
					default -> throw new UnsupportedOperationException();
				};
			}
		}

		@Override
		public String toString(BooleanExpression... operands) throws InvalidArgumentCountException {
			InvalidArgumentCountException.checkExact(1, operands.length);

			return wrapWithParentheses(operands[0]) + "′";
		}
	}, NAND {
		@Override
		public BooleanExpression applyTo(BooleanExpression... operands) {
			InvalidArgumentCountException.checkExact(2, operands.length);

			ExpressionTreeNode firstExpressionRootNode = ExpressionTreeNode.fromBooleanExpression(operands[0]);
			ExpressionTreeNode secondExpressionRootNode = ExpressionTreeNode.fromBooleanExpression(operands[1]);

			return new CompoundBooleanExpression(
					OperatorNode.forNAND(firstExpressionRootNode, secondExpressionRootNode));
		}

		@Override
		public BooleanExpression applyUsing(FunctionallyCompleteOperatorsSet operatorsSet,
											BooleanExpression... operands) throws InvalidArgumentCountException, UnsupportedOperationException {
			throw new UnsupportedOperationException();
		}

		@Override
		public String toString(BooleanExpression... operands) throws InvalidArgumentCountException {
			InvalidArgumentCountException.checkExact(2, operands.length);

			return buildNAryOperatorString(" | ", operands);
		}
	}, NOR {
		@Override
		public BooleanExpression applyTo(BooleanExpression... operands) {
			InvalidArgumentCountException.checkExact(2, operands.length);

			ExpressionTreeNode firstExpressionRootNode = ExpressionTreeNode.fromBooleanExpression(operands[0]);
			ExpressionTreeNode secondExpressionRootNode = ExpressionTreeNode.fromBooleanExpression(operands[1]);

			return new CompoundBooleanExpression(
					OperatorNode.forNOR(firstExpressionRootNode, secondExpressionRootNode));
		}

		@Override
		public BooleanExpression applyUsing(FunctionallyCompleteOperatorsSet operatorsSet,
											BooleanExpression... operands) throws InvalidArgumentCountException, UnsupportedOperationException {
			throw new UnsupportedOperationException();
		}

		@Override
		public String toString(BooleanExpression... operands) throws InvalidArgumentCountException {
			InvalidArgumentCountException.checkExact(2, operands.length);

			return buildNAryOperatorString(" ↓ ", operands);
		}
	};

	private static BooleanExpression[] expressViaOperators(FunctionallyCompleteOperatorsSet operatorsSet,
														   BooleanExpression... expressions) {
		BooleanExpression[] newExpressions = new BooleanExpression[expressions.length];
		for (int i = 0; i < expressions.length; i++) {
			BooleanExpression expression = expressions[i];

			newExpressions[i] = expression.expressViaOperators(operatorsSet);
		}

		return newExpressions;
	}

	private static String buildNAryOperatorString(String delimiter, BooleanExpression... operands) {
		StringJoiner joiner = new StringJoiner(delimiter);
		for (BooleanExpression operand : operands) {
			joiner.add(wrapWithParentheses(operand));
		}

		return joiner.toString();
	}

	private static String wrapWithParentheses(BooleanExpression expression) {
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
