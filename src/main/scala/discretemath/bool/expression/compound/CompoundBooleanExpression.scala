package discretemath.bool.expression.compound

import discretemath.bool.expression.BooleanExpression
import discretemath.bool.expression.atomic.BooleanVariable
import discretemath.bool.expression.exception.VariableValueNotSpecifiedException
import discretemath.bool.operator.FunctionallyCompleteOperatorsSet
import discretemath.bool.operator.Operators.NOT

final class CompoundBooleanExpression(val rootNode: OperatorNode) extends BooleanExpression {
  assert(rootNode != null, "Root not may not be null")

  @throws[VariableValueNotSpecifiedException]
  def evaluate(arguments: Map[BooleanVariable, Boolean]): Boolean = CompoundBooleanExpression.evaluateNode(rootNode, arguments)

  def expressViaOperators(operatorsSet: FunctionallyCompleteOperatorsSet): BooleanExpression = CompoundBooleanExpression.expressExpressionViaOperators(this.rootNode, operatorsSet)

  override def minimize: BooleanExpression = {
    val newRootNode = CompoundBooleanExpression.deleteDoubleNegations(rootNode, negationAlreadyFound = false).asInstanceOf[OperatorNode]
    new CompoundBooleanExpression(newRootNode)
  }

  override def toString: String = rootNode.toString
}

object CompoundBooleanExpression {

  def apply(rootNode: OperatorNode): CompoundBooleanExpression = new CompoundBooleanExpression(rootNode)

  private def evaluateNode(node: ExpressionTreeNode, values: Map[BooleanVariable, Boolean]): Boolean = {
    node match {
      case ValueNode(expression) => expression.evaluate(values)
      case OperatorNode(operator, operands) => operator.evaluate(operands.map(evaluateNode(_, values)))
    }
  }

  private def expressExpressionViaOperators(node: ExpressionTreeNode, operatorsSet: FunctionallyCompleteOperatorsSet): BooleanExpression = {
    node match {
      case ValueNode(expression) => expression.expressViaOperators(operatorsSet)
      case OperatorNode(operator, operands) => operator.injectUsing(operatorsSet, ExpressionTreeNode.toBooleanExpression(operands))
    }
  }

  private def deleteDoubleNegations(node: ExpressionTreeNode, negationAlreadyFound: Boolean): ExpressionTreeNode = {
    node match {
      case ValueNode(_) => if (negationAlreadyFound) OperatorNode.not(node) else node
      case OperatorNode(operator, operands) if operator == NOT => {
        val operand = operands.head
        if (negationAlreadyFound) deleteDoubleNegations(operand, negationAlreadyFound = false)
        else deleteDoubleNegations(operand, negationAlreadyFound = true)
      }
      case OperatorNode(operator, operands) => {
        val newOperands = operands.map(operand => deleteDoubleNegations(operand, negationAlreadyFound = false))

        val newOperatorNode = OperatorNode(operator, newOperands)
        return if (negationAlreadyFound) OperatorNode.not(newOperatorNode) else newOperatorNode
      }
    }
  }
}