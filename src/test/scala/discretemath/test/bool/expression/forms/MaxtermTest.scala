package discretemath.test.bool.expression.forms

import discretemath.bool.expression.atomic.BooleanVariable.{X, Y, Z}
import discretemath.bool.expression.forms.{Literal, Maxterm}
import discretemath.bool.operator.FunctionallyCompleteOperatorsSet._
import org.scalatest.funsuite.AnyFunSuite

import scala.collection.immutable.ListSet

class MaxtermTest extends AnyFunSuite {

  test("expressViaOperators") {
    val literal1 = Literal + X
    val literal2 = Literal ~ Y
    val literal3 = Literal ~ Z

    val maxterm = Maxterm(ListSet(literal1, literal2, literal3))
    assert("x + ~y + ~z" == maxterm.expressViaOperators(OR_NOT).toString)
    assert("x + ~y + ~z" == maxterm.expressViaOperators(AND_OR_NOT).toString)
    assert("~(~xyz)" == maxterm.expressViaOperators(AND_NOT).minimize.toString)
    assert("(x ↑ x) ↑ ((y ↑ y) ↑ (y ↑ y)) ↑ ((z ↑ z) ↑ (z ↑ z))" == maxterm.expressViaOperators(ONLY_NAND).toString)
    assert("(x ↓ (y ↓ y) ↓ (z ↓ z)) ↓ (x ↓ (y ↓ y) ↓ (z ↓ z))" == maxterm.expressViaOperators(ONLY_NOR).toString)
  }
}
