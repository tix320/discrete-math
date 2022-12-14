package discretemath.test.bool.expression

import discretemath.bool.expression.atomic.BooleanVariable.{X, Y}
import discretemath.bool.expression.compound.{CompoundBooleanExpression, OperatorNode, ValueNode}
import discretemath.bool.operator.FunctionallyCompleteOperatorsSet.{AND_NOT, ONLY_NAND, ONLY_NOR, OR_NOT}
import discretemath.bool.operator.Operators.OR
import org.scalatest.funsuite.AnyFunSuite

class BooleanExpressionTest extends AnyFunSuite {

  test("expressViaOperators") {

    val node1 = ValueNode(X)
    val node2 = OperatorNode.not(ValueNode(Y))
    val node3 = ValueNode(Y)
    val node4 = OperatorNode.not(ValueNode(X))

    val node5 = OperatorNode * (OR, node1, node2)
    val node6 = OperatorNode.not(node5)

    val node7 = OperatorNode * (OR, node3, node4)

    val rootNode = OperatorNode * (OR, node6, node7)


    val expression = new CompoundBooleanExpression(rootNode)

    assert(expression.toString == "~(x + ~y) + (y + ~x)")

    val viaANDExpression = expression.expressViaOperators(AND_NOT)
    val normalizedViaANDExpression = viaANDExpression.minimize

    assert(viaANDExpression.toString == "~(~~~(~x~~y)~~(~y~~x))")
    assert(normalizedViaANDExpression.toString == "~(~(~xy)(~yx))")

    val viaORExpression = normalizedViaANDExpression.expressViaOperators(OR_NOT)
    val normalizedViaORExpression = viaORExpression.minimize

    assert(viaORExpression.toString == "~~(~~~(~~x + ~y) + ~~(~~y + ~x))")
    assert(normalizedViaORExpression.toString == "~(x + ~y) + (y + ~x)")

    val viaNANDExpression = expression.expressViaOperators(ONLY_NAND)

    assert(viaNANDExpression.toString == "((((x ↑ x) ↑ ((y ↑ y) ↑ (y ↑ y))) ↑ ((x ↑ x) ↑ ((y ↑ y) ↑ (y ↑ y)))) ↑ (((x ↑ x) ↑ ((y ↑ y) ↑ (y ↑ y))) ↑ ((x ↑ x) ↑ ((y ↑ y) ↑ (y ↑ y))))) ↑ (((y ↑ y) ↑ ((x ↑ x) ↑ (x ↑ x))) ↑ ((y ↑ y) ↑ ((x ↑ x) ↑ (x ↑ x))))")

    val viaNORExpression = expression.expressViaOperators(ONLY_NOR)

    assert(viaNORExpression.toString == "((((x ↓ (y ↓ y)) ↓ (x ↓ (y ↓ y))) ↓ ((x ↓ (y ↓ y)) ↓ (x ↓ (y ↓ y)))) ↓ ((y ↓ (x ↓ x)) ↓ (y ↓ (x ↓ x)))) ↓ ((((x ↓ (y ↓ y)) ↓ (x ↓ (y ↓ y))) ↓ ((x ↓ (y ↓ y)) ↓ (x ↓ (y ↓ y)))) ↓ ((y ↓ (x ↓ x)) ↓ (y ↓ (x ↓ x))))")
  }
}
