package discretemath.structure.sequence

import discretemath.common.Adder
import discretemath.structure.set.main._
import discretemath.structure.function._
import discretemath.structure.set.multi.MultiSet

abstract class SequenceDecorator[T](protected val originSequence: Sequence[T]) extends Sequence[T] {

  override def domain: Set[BigInt] = originSequence.domain

  override def codomain: Set[T] = originSequence.codomain

  override def range: Set[T] = originSequence.range

  override def multiSetRange: MultiSet[T] = originSequence.multiSetRange

  override def isOnto: Boolean = originSequence.isOnto

  override def isOneToOne: Boolean = originSequence.isOneToOne

  override def getInverse: Function[T, BigInt] = originSequence.getInverse

  override def graph: Set[(BigInt, T)] = originSequence.graph

  override def apply(input: BigInt): T = originSequence.apply(input)

  override def sum(implicit adder: Adder[T], from: Int, to: Int): T = originSequence.sum(adder, from, to)
}
