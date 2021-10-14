package discretemath.structure.sequence

import discretemath.common.Adder
import discretemath.structure.exception.NotSummableException
import discretemath.structure.function.AbstractFunction
import discretemath.structure.set.Sets
import discretemath.structure.set.main._

import scala.math.BigInt

abstract class AbstractSequence[T] protected(val codomain: Set[T]) extends AbstractFunction[BigInt, T] with Sequence[T] {

  override final def domain: Set[BigInt] = Sets.N

  @throws[NotSummableException]
  override def sum(implicit adder: Adder[T], from: Int, to: Int) = throw new UnsupportedOperationException("Not implemented yet")
}