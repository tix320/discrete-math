package discretemath.structure.sequence

import discretemath.structure.set.main._

abstract class SimpleSequence[T](override val codomain: Set[T]) extends AbstractSequence[T](codomain) {
}