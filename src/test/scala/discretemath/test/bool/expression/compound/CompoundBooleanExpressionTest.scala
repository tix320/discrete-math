package discretemath.test.bool.expression.compound

import discretemath.bool.expression.atomic.BooleanVariable.{X, Y}
import discretemath.bool.expression.compound.{CompoundBooleanExpression, OperatorNode, ValueNode}
import discretemath.bool.operator.Operators.{AND, OR}
import org.scalatest.funsuite.AnyFunSuite

class CompoundBooleanExpressionTest extends AnyFunSuite {

  test("getVariables") {
    val node1 = ValueNode(X)
    val node2 = OperatorNode.not(ValueNode(Y))
    val node3 = ValueNode(Y)
    val node4 = OperatorNode.not(ValueNode(X))

    val node5 = OperatorNode * (OR, node1, node2)
    val node6 = OperatorNode.not(node5)

    val node7 = OperatorNode * (OR, node3, node4)

    val rootNode = OperatorNode * (OR, node6, node7)


    val expression = new CompoundBooleanExpression(rootNode)

    assert(expression.getVariables == Set(X, Y))
  }

  test("isSatisfiableTrue") {
    val node1 = ValueNode(X)
    val node2 = OperatorNode.not(ValueNode(Y))
    val node3 = ValueNode(Y)
    val node4 = OperatorNode.not(ValueNode(X))

    val node5 = OperatorNode * (OR, node1, node2)
    val node6 = OperatorNode.not(node5)

    val node7 = OperatorNode * (OR, node3, node4)

    val rootNode = OperatorNode * (OR, node6, node7)


    val expression = new CompoundBooleanExpression(rootNode)

    assert(expression.isSatisfiable)
  }

  test("isSatisfiableFalse") {
    val node1 = ValueNode(X)
    val node2 = OperatorNode.not(ValueNode(X))

    val rootNode = OperatorNode * (AND, node1, node2)

    val expression = new CompoundBooleanExpression(rootNode)

    assert(!expression.isSatisfiable)
  }

}
