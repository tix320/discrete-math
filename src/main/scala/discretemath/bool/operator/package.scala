package discretemath.bool

import discretemath.bool.expression.BooleanExpression
import discretemath.bool.expression.compound.{CompoundBooleanExpression, OperatorNode, ValueNode}
import discretemath.bool.operator.Operators.NOT

import java.util.StringJoiner

package object operator {

  def buildNAryOperatorString(delimiter: String, operands: collection.Seq[BooleanExpression]): String = {
    val joiner = new StringJoiner(delimiter)
    for (operand <- operands) {
      joiner.add(wrapWithParentheses(operand))
    }
    joiner.toString
  }

  def wrapWithParentheses(expression: BooleanExpression): String = {
    expression match {
      case compoundBooleanExpression: CompoundBooleanExpression => {
        if (compoundBooleanExpression.rootNode.operator == NOT) {
          return expression.toString
        } else {
          return "(" + expression.toString + ")"
        }
      }
      case _ => expression.toString
    }
  }

  def expressViaOperators(operatorsSet: FunctionallyCompleteOperatorsSet, expressions: collection.Seq[BooleanExpression]): Seq[BooleanExpression] = {
    return expressions.view.map(expression => expression.expressViaOperators(operatorsSet)).toSeq
  }

  def injectOperator(operator: Operator, operands: Seq[BooleanExpression]): BooleanExpression = {
    val expressionNodes = operands.map {
      case compoundBooleanExpression: CompoundBooleanExpression => compoundBooleanExpression.rootNode
      case operand => ValueNode(operand)
    }

    val rootNode = OperatorNode(operator, expressionNodes)
    new CompoundBooleanExpression(rootNode)
  }
}
