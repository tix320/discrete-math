package discretemath.bool.expression.compound

import discretemath.bool.expression.BooleanExpression
import discretemath.bool.expression.atomic.BooleanVariable
import discretemath.bool.expression.compound.CompoundBooleanExpression.collectNodeVariables
import discretemath.bool.expression.exception.VariableValueNotSpecifiedException
import discretemath.bool.operator.FunctionallyCompleteOperatorsSet
import discretemath.bool.operator.Operators.NOT
import discretemath.combination.BitCombinations

import scala.collection.mutable

final class CompoundBooleanExpression(val rootNode: OperatorNode) extends BooleanExpression {
  assert(rootNode != null, "Root not may not be null")

  override def getVariables: Set[BooleanVariable] = {
    val allVariables = new mutable.HashSet[BooleanVariable]()
    collectNodeVariables(rootNode, allVariables)

    allVariables.toSet // TODO redundant copy for immutable set
  }

  @throws[VariableValueNotSpecifiedException]
  override def evaluate(arguments: Map[BooleanVariable, Boolean]): Boolean = CompoundBooleanExpression.evaluateNode(rootNode, arguments)

  override def expressViaOperators(operatorsSet: FunctionallyCompleteOperatorsSet): BooleanExpression = CompoundBooleanExpression.expressNodeViaOperators(this.rootNode, operatorsSet)

  override def minimize: BooleanExpression = {
    val newRootNode = CompoundBooleanExpression.deleteDoubleNegations(rootNode, negationAlreadyFound = false).asInstanceOf[OperatorNode]
    new CompoundBooleanExpression(newRootNode)
  }

  override def isSatisfiable: Boolean = {
    val variablesList = getVariables.toIndexedSeq
    val allPossibleValues = BitCombinations.nBitCombinations(variablesList.size)


    for (i <- 0 until allPossibleValues.rowsCount) {
      val values = allPossibleValues.getRow(i)

      val variableMap = variablesList.zipWithIndex.map(tuple => (tuple._1, values(tuple._2))).toMap

      val result = evaluate(variableMap)

      if (result) {
        return true
      }
    }

    return false
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

  private def collectNodeVariables(node: ExpressionTreeNode, variables: mutable.Set[BooleanVariable]): Unit = {
    node match {
      case ValueNode(expression) => variables.addAll(expression.getVariables)
      case OperatorNode(_, operands) => operands.foreach(operand => collectNodeVariables(operand, variables))
    }
  }

  private def expressNodeViaOperators(node: ExpressionTreeNode, operatorsSet: FunctionallyCompleteOperatorsSet): BooleanExpression = {
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