package discretemath.test.bool.operator

import discretemath.bool.operator.ThresholdOperator
import org.scalatest.funsuite.AnyFunSuite

class ThresholdOperatorTest extends AnyFunSuite {

  test("evaluate") {
    val thresholdOperator = new ThresholdOperator(0.5, Array[Double](-1, 1, 2))

    assert(!thresholdOperator.%(false, false, false))
    assert(thresholdOperator.%(false, false, true))
    assert(thresholdOperator.%(false, true, false))
    assert(thresholdOperator.%(false, true, true))
    assert(!thresholdOperator.%(true, false, false))
    assert(thresholdOperator.%(true, false, true))
    assert(!thresholdOperator.%(true, true, false))
    assert(thresholdOperator.%(true, true, true))
  }
}