package discretemath.bool.expression.compound

import discretemath.bool.expression.BooleanExpression
import discretemath.bool.operator.Operator
import discretemath.bool.operator.Operators.{BICONDITIONAL, CONDITIONAL, NOT}

sealed trait ExpressionTreeNode {
  def getHeight: Int
}

object ExpressionTreeNode {
  def fromBooleanExpression(expression: BooleanExpression): ExpressionTreeNode = {
    expression match {
      case compoundExpression: CompoundBooleanExpression => compoundExpression.rootNode
      case _ => ValueNode(expression)
    }
  }

  def fromBooleanExpression(expressions: collection.Seq[BooleanExpression]): Seq[ExpressionTreeNode] = expressions.map(fromBooleanExpression).toSeq

  def toBooleanExpression(node: ExpressionTreeNode): BooleanExpression = {
    node match {
      case ValueNode(expression) => expression
      case operatorNode: OperatorNode => new CompoundBooleanExpression(operatorNode)
    }
  }

  def toBooleanExpression(nodes: collection.Seq[ExpressionTreeNode]): Seq[BooleanExpression] = nodes.map(toBooleanExpression).toSeq
}

case class OperatorNode(operator: Operator, operands: Seq[ExpressionTreeNode]) extends ExpressionTreeNode {

  override def getHeight: Int = 1 + operands.map(o => o.getHeight).max

  override def toString: String = operator.toString(ExpressionTreeNode.toBooleanExpression(operands))
}

object OperatorNode {

  def *(operator: Operator, operands: ExpressionTreeNode*): OperatorNode = OperatorNode(operator, operands)

  def not(operand: ExpressionTreeNode): OperatorNode = new OperatorNode(NOT, Seq(operand))

  def conditional(hypothesis: ExpressionTreeNode, conclusion: ExpressionTreeNode): OperatorNode = new OperatorNode(CONDITIONAL, Seq(hypothesis, conclusion))

  def biconditional(operand1: ExpressionTreeNode, operand2: ExpressionTreeNode): OperatorNode = new OperatorNode(BICONDITIONAL, Seq(operand1, operand2))
}

case class ValueNode(expression: BooleanExpression) extends ExpressionTreeNode {
  override def getHeight = 0

  override def toString: String = expression.toString
}
