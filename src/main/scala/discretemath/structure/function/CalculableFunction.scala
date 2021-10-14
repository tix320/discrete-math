package discretemath.structure.function

import discretemath.structure.set.main._

abstract class CalculableFunction[T, R](val domain: Set[T], val codomain: Set[R]) extends AbstractFunction[T, R] {

  override final protected def applyWithoutCheckInput(input: T): R = {
    val result = calculate(input)

    if (result == null) throw new IllegalStateException("Result for input [%s] is not defined".format(input))

    if (!codomain.contains(result)) throw new IllegalStateException("Output [%s] does not exist in the codomain".format(result))

    return result
  }

  def calculate(input: T): R
}