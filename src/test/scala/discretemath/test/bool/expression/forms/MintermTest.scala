package discretemath.test.bool.expression.forms

import discretemath.bool.expression.atomic.BooleanVariable.{X, Y, Z}
import discretemath.bool.expression.forms.{Literal, Minterm}
import discretemath.bool.operator.FunctionallyCompleteOperatorsSet._
import org.scalatest.funsuite.AnyFunSuite

import scala.collection.immutable.ListSet

class MintermTest extends AnyFunSuite {

  test("expressViaOperators") {
    val literal1 = Literal ~ X
    val literal2 = Literal ~ Y
    val literal3 = Literal + Z

    val minterm = Minterm(ListSet(literal1, literal2, literal3))
    assert("~x~yz" == minterm.expressViaOperators(AND_NOT).toString)
    assert("~x~yz" == minterm.expressViaOperators(AND_OR_NOT).toString)
    assert("~(x + y + ~z)" == minterm.expressViaOperators(OR_NOT).minimize.toString)
    assert("((x ↑ x) ↑ (y ↑ y) ↑ z) ↑ ((x ↑ x) ↑ (y ↑ y) ↑ z)" == minterm.expressViaOperators(ONLY_NAND).toString)
    assert("((x ↓ x) ↓ (x ↓ x)) ↓ ((y ↓ y) ↓ (y ↓ y)) ↓ (z ↓ z)" == minterm.expressViaOperators(ONLY_NOR).toString)
  }
}
