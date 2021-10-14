package discretemath.structure.sequence

import discretemath.structure.set.main._

import scala.math.BigInt

abstract class RecurrentSequence[T](override val codomain: Set[T]) extends AbstractSequence[T](codomain) {

  final override def applyWithoutCheckInput(input: BigInt): T = getNextRecurrentValue(input)

  protected def getNextRecurrentValue(n: BigInt): T
}