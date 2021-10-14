package discretemath.test.bool.expression.forms

import discretemath.bool.expression.atomic.BooleanVariables.{X, Y, Z}
import discretemath.bool.expression.forms.ProductOfSumExpression
import discretemath.bool.table.{FunctionValue, TruthTable}
import org.scalatest.funsuite.AnyFunSuite

class ProductOfSumExpressionTest extends AnyFunSuite {

  test("evaluate") {
    val truthTable = TruthTable.forNDegree(3, 1)
    truthTable.setFunctionValue(0, Array[FunctionValue](FunctionValue.TRUE, FunctionValue.FALSE, FunctionValue.FALSE, FunctionValue.TRUE, FunctionValue.TRUE, FunctionValue.TRUE, FunctionValue.FALSE, FunctionValue.FALSE))
    val expression = ProductOfSumExpression(truthTable)
    assert(expression.toString == "(x+y+~z)(x+~y+z)(~x+~y+z)(~x+~y+~z)")
    assert(expression.evaluate(Map(X -> false, Y -> false, Z -> false)))
    assert(!expression.evaluate(Map(X -> false, Y -> false, Z -> true)))
    assert(!expression.evaluate(Map(X -> false, Y -> true, Z -> false)))
    assert(expression.evaluate(Map(X -> false, Y -> true, Z -> true)))
    assert(expression.evaluate(Map(X -> true, Y -> false, Z -> false)))
    assert(expression.evaluate(Map(X -> true, Y -> false, Z -> true)))
    assert(!expression.evaluate(Map(X -> true, Y -> true, Z -> false)))
    assert(!expression.evaluate(Map(X -> true, Y -> true, Z -> true)))
  }
}
