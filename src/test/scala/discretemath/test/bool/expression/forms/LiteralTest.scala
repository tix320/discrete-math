package discretemath.test.bool.expression.forms

import discretemath.bool.expression.atomic.BooleanVariable.X
import discretemath.bool.expression.forms.Literal
import discretemath.bool.operator.FunctionallyCompleteOperatorsSet._
import org.scalatest.funsuite.AnyFunSuite

class LiteralTest extends AnyFunSuite {

  test("expressViaOperators") {

    val literal = Literal + X
    assert("x" == literal.expressViaOperators(OR_NOT).toString)
    assert("x" == literal.expressViaOperators(AND_NOT).toString)
    assert("x" == literal.expressViaOperators(AND_OR_NOT).toString)
    assert("x" == literal.expressViaOperators(ONLY_NAND).toString)
    assert("x" == literal.expressViaOperators(ONLY_NOR).toString)

    val notLiteral = Literal ~ X
    assert("~x" == notLiteral.expressViaOperators(OR_NOT).toString)
    assert("~x" == notLiteral.expressViaOperators(AND_NOT).toString)
    assert("~x" == notLiteral.expressViaOperators(AND_OR_NOT).toString)
    assert("x ↑ x" == notLiteral.expressViaOperators(ONLY_NAND).toString)
    assert("x ↓ x" == notLiteral.expressViaOperators(ONLY_NOR).toString)
  }
}
