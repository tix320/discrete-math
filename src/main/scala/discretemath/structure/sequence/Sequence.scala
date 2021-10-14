package discretemath.structure.sequence

import discretemath.common.Adder
import discretemath.structure.exception.NotSummableException
import discretemath.structure.function._

import scala.math.BigInt

trait Sequence[T] extends Function[BigInt, T] {

  @throws[NotSummableException]
  def sum(implicit adder: Adder[T], from: Int, to: Int): T
}