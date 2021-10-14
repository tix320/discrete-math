package discretemath.test.bool.expression.forms

import discretemath.bool.expression.atomic.BooleanVariables
import discretemath.bool.expression.forms.{Literal, Minterm}
import discretemath.bool.operator.FunctionallyCompleteOperatorsSet._
import org.scalatest.funsuite.AnyFunSuite

import scala.collection.immutable.ListSet

class MintermTest extends AnyFunSuite {

  test("expressViaOperators") {
    val variable1 = BooleanVariables.X
    val variable2 = BooleanVariables.Y
    val variable3 = BooleanVariables.Z

    val literal1 = Literal ~ variable1
    val literal2 = Literal ~ variable2
    val literal3 = Literal + variable3

    val minterm = Minterm(ListSet(literal1, literal2, literal3))
    assert("~x~yz" == minterm.expressViaOperators(AND_NOT).toString)
    assert("~x~yz" == minterm.expressViaOperators(AND_OR_NOT).toString)
    assert("~(x + y + ~z)" == minterm.expressViaOperators(OR_NOT).minimize.toString)
    assert("((x ↑ x) ↑ (y ↑ y) ↑ z) ↑ ((x ↑ x) ↑ (y ↑ y) ↑ z)" == minterm.expressViaOperators(ONLY_NAND).toString)
    assert("((x ↓ x) ↓ (x ↓ x)) ↓ ((y ↓ y) ↓ (y ↓ y)) ↓ (z ↓ z)" == minterm.expressViaOperators(ONLY_NOR).toString)
  }
}
