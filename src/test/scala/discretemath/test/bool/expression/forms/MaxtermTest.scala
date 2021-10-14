package discretemath.test.bool.expression.forms

import discretemath.bool.expression.atomic.{BooleanVariable, BooleanVariables}
import discretemath.bool.expression.forms.{Literal, Maxterm}
import discretemath.bool.operator.FunctionallyCompleteOperatorsSet
import discretemath.bool.operator.FunctionallyCompleteOperatorsSet._
import org.scalatest.funsuite.AnyFunSuite

import scala.collection.immutable.ListSet

class MaxtermTest extends AnyFunSuite {

  test("expressViaOperators") {
    val variable1 = BooleanVariables.X
    val variable2 = BooleanVariables.Y
    val variable3 = BooleanVariables.Z

    val literal1 = Literal +variable1
    val literal2 = Literal ~variable2
    val literal3 = Literal ~variable3

    val maxterm = Maxterm(ListSet(literal1,literal2,literal3))
    assert("x + ~y + ~z" == maxterm.expressViaOperators(OR_NOT).toString)
    assert("x + ~y + ~z" == maxterm.expressViaOperators(AND_OR_NOT).toString)
    assert("~(~xyz)" == maxterm.expressViaOperators(AND_NOT).minimize.toString)
    assert("(x ↑ x) ↑ ((y ↑ y) ↑ (y ↑ y)) ↑ ((z ↑ z) ↑ (z ↑ z))" == maxterm.expressViaOperators(ONLY_NAND).toString)
    assert("(x ↓ (y ↓ y) ↓ (z ↓ z)) ↓ (x ↓ (y ↓ y) ↓ (z ↓ z))" == maxterm.expressViaOperators(ONLY_NOR).toString)
  }
}
