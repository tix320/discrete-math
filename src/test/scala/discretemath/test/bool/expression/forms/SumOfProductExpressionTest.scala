package discretemath.test.bool.expression.forms

import discretemath.bool.expression.atomic.BooleanVariables.{X, Y, Z}
import discretemath.bool.expression.forms.SumOfProductExpression
import discretemath.bool.table.FunctionValue.{FALSE, TRUE}
import discretemath.bool.table.{FunctionValue, TruthTable}
import org.scalatest.funsuite.AnyFunSuite

class SumOfProductExpressionTest extends AnyFunSuite {

  test("evaluate") {
    val truthTable = TruthTable.forNDegree(3, 1)
    truthTable.setFunctionValue(0, Array[FunctionValue](TRUE, FALSE, TRUE, FALSE, FALSE, TRUE, TRUE, FALSE))
    val expression = SumOfProductExpression(truthTable)
    assert(expression.toString == "~x~y~z + ~xy~z + x~yz + xy~z")
    assert(expression.evaluate(Map(X -> false, Y -> false, Z -> false)))
    assert(!expression.evaluate(Map(X -> false, Y -> false, Z -> true)))
    assert(expression.evaluate(Map(X -> false, Y -> true, Z -> false)))
    assert(!expression.evaluate(Map(X -> false, Y -> true, Z -> true)))
    assert(!expression.evaluate(Map(X -> true, Y -> false, Z -> false)))
    assert(expression.evaluate(Map(X -> true, Y -> false, Z -> true)))
    assert(expression.evaluate(Map(X -> true, Y -> true, Z -> false)))
    assert(!expression.evaluate(Map(X -> true, Y -> true, Z -> true)))
  }
}