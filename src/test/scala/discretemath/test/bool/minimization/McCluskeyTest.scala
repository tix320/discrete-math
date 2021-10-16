package discretemath.test.bool.minimization

import discretemath.bool.expression.atomic.BooleanVariable
import discretemath.bool.minimization.McCluskey
import discretemath.bool.table.FunctionValue.{DONT_CARE, FALSE, TRUE}
import discretemath.bool.table.{FunctionValue, TruthTable}
import org.scalatest.funsuite.AnyFunSuite

class McCluskeyTest extends AnyFunSuite {

  test("applyToTruthTable1") {
    val variablesCount = 4
    val truthTable = TruthTable.forNDegree(variablesCount, 1)
    truthTable.setFunctionValue(0, Array[FunctionValue](FALSE, FALSE, FALSE, FALSE, TRUE, FALSE, FALSE, FALSE, TRUE, DONT_CARE, TRUE, TRUE, TRUE, FALSE, DONT_CARE, TRUE))
    val variables = BooleanVariable.getN(variablesCount)
    val booleanExpression = McCluskey.minimizeFromTruthTable(truthTable, variables)
    assert(booleanExpression.toString == "(x~y) + (y~z~w) + (xz)")
  }

  test("applyToTruthTable2") {
    val variablesCount = 4
    val truthTable = TruthTable.forNDegree(variablesCount, 1)
    truthTable.setFunctionValue(0, Array[FunctionValue](TRUE, TRUE, TRUE, TRUE, TRUE, FALSE, FALSE, FALSE, TRUE, TRUE, TRUE, FALSE, FALSE, FALSE, TRUE, FALSE))
    val variables = BooleanVariable.getN(variablesCount)
    val booleanExpression = McCluskey.minimizeFromTruthTable(truthTable, variables)
    assert(booleanExpression.toString == "(~x~y) + (~y~z) + (xz~w) + (~x~z~w)")
  }

  test("applyToTruthTable3") {
    val variablesCount = 4
    val truthTable = TruthTable.forNDegree(variablesCount, 1)
    truthTable.setFunctionValue(0, Array[FunctionValue](DONT_CARE, FALSE, TRUE, TRUE, DONT_CARE, DONT_CARE, FALSE, FALSE, FALSE, DONT_CARE, TRUE, DONT_CARE, TRUE, FALSE, TRUE, FALSE))
    val variables = BooleanVariable.getN(variablesCount)
    val booleanExpression = McCluskey.minimizeFromTruthTable(truthTable, variables)
    assert(booleanExpression.toString == "(~yz) + (xy~w)")
  }

  test("applyToTruthTable4") {
    val variablesCount = 5
    val truthTable = TruthTable.forNDegree(variablesCount, 1)
    truthTable.setFunctionValue(0, Array[FunctionValue](DONT_CARE, FALSE, TRUE, TRUE, FALSE, DONT_CARE, TRUE, FALSE, TRUE, DONT_CARE, TRUE, FALSE, FALSE, FALSE, FALSE, DONT_CARE, TRUE, FALSE, TRUE, TRUE, DONT_CARE, TRUE, TRUE, FALSE, FALSE, DONT_CARE, FALSE, FALSE, DONT_CARE, DONT_CARE, DONT_CARE, DONT_CARE))
    val variables = BooleanVariable.getN(variablesCount)
    val booleanExpression = McCluskey.minimizeFromTruthTable(truthTable, variables)
    assert(booleanExpression.toString == "(~y~zw) + (~x~z~a) + (~yw~a) + (~y~z~a) + (xz~w)")
  }
}